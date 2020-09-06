package app.assem.lavillasweets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Orders.Order;

/**
 * Это класс, где прописывается вся логика, наследуется от RecycleView,
 * потому что в шаблоне я использую RecycleView. Чтобы туда передать данные, нужно делать вот так.
 */
public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> implements Filterable {
    private Context context;
    private List<Order> orders;
    private List<Order> filteredOrderList;

    OrdersAdapter(Context context, List<Order> nameList) {
        super();
        this.context = context;
        this.orders = nameList;
        this.filteredOrderList = nameList;
    }


    //Сам поиск
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSequenceString = constraint.toString().toLowerCase();
                if (charSequenceString.isEmpty()) {
                    filteredOrderList = orders;
                } else {
                    List<Order> filteredList = new ArrayList<>();
                    //Для того, чтобы сделать двоичный поиск надо отсортировать массив
                    //Используем стандартную функцию, она использует быструю сортировку
                    //передаем в нее компаратор, чтобы она сортировала объекты по полю - название сладости
                    Collections.sort(orders, new Comparator<Order>() {
                        @Override
                        public int compare(Order order1, Order order2) {
                            return order1.getConfectionaryname().compareTo(order2.getConfectionaryname());
                        }
                    });

//                    for(int i = 0; i < orders.size(); i++){
//                        System.out.println(i + ": " + orders.get(i).getConfectionaryname());
//                    }

                    //Так как бинарный поиск ищет только одно вхождение элемента
                    //Используем левосторонний - ищет первое вхождение, и правосторонний - последнее
                    //вхождение элемента, в ответ добавляем все элементы от первого до последнего.

                    int firstIndex = binarySearchFirstOccurence(orders, charSequenceString);
                    int lastIndex = binarySearchLastOccurence(orders, charSequenceString);
                    if(firstIndex == -1) {
                        filteredList.addAll(orders);
                    } else {
//                        filteredList.add(orders.get(firstIndex));
                        for(int i = firstIndex; i <= lastIndex; i++){
                            filteredList.add(orders.get(i));
                        }
                    }
                    filteredOrderList = filteredList;

                    //Здесь был линейный поиск, если вдруг что-то пойдет не так с бинарным раскоменть просто
//                    for (Order order : orders) {
//                        if (order.getConfectionaryname().toLowerCase().contains(charSequenceString.toLowerCase())) {
//                            filteredList.add(name);
//                        }
//                        filteredOrderList = filteredList;
//                    }

                }
                FilterResults results = new FilterResults();
                results.values = filteredOrderList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredOrderList = (List<Order>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static int binarySearchFirstOccurence(List<Order> orders, String key) {
        int low=0;
        int high=orders.size()-1;
        int result=-1;
        while(low<=high){
            int mid=(low+high)/2;
            int res = orders.get(mid).getConfectionaryname().compareTo(key);
            if(res<0) {
                low = mid + 1;
            }
            else if(res>0) {
                high = mid - 1;
            }
            else {
                result=mid;
                high=mid-1;
            }
        }
        return result;
    }

    public static int binarySearchLastOccurence(List<Order> orders, String key) {
        int low=0;
        int high=orders.size()-1;
        int result = -1;
        while(low<=high){
            int mid=(low+high)/2;
            int res = orders.get(mid).getConfectionaryname().compareTo(key);
            if(res<0) {
                low = mid + 1;
            } else if(res>0) {
                high = mid - 1;
            } else {
                result=mid;
                low=mid+1;
            }
        }
        return result;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_layout, viewGroup, false);
        return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
//        holder.id.setText(filteredNameList.get(position).getId());
        String text = filteredOrderList.get(position).getFullname() + "\n"
                      + filteredOrderList.get(position).getAddress() + "\n"
                      + filteredOrderList.get(position).getDate() + "\n"
                      + filteredOrderList.get(position).getConfectionaryname()+ "\n"
                      + filteredOrderList.get(position).getQuantity();
        holder.fullname.setText(text);
//        holder.address.setText();
//        holder.date.setText();
//        holder.confectionaryName.setText();
//        holder.quantity.setText();
    }

    @Override
    public int getItemCount() {
        return filteredOrderList.size();
    }

    static class OrdersViewHolder extends RecyclerView.ViewHolder {

//        private AppCompatTextView id;
        private AppCompatTextView fullname;
        private AppCompatTextView address;
        private AppCompatTextView date;
        private AppCompatTextView confectionaryName;
        private AppCompatTextView quantity;


        OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
//            id = itemView.findViewById(R.id.id);
            fullname = itemView.findViewById(R.id.fullname);
            address = itemView.findViewById(R.id.address);
            date = itemView.findViewById(R.id.date);
            confectionaryName = itemView.findViewById(R.id.confectionary_name);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}


