package app.assem.lavillasweets;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.ActionBar;
import androidx.core.view.MenuItemCompat;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import DBHelper.DatabaseHandler;
import Orders.Order;
import Utils.Util;

/**
 * Получаем инфу из базы данных, я оптимизировала вашу реализацию
 * Но ничего не удалила
 */

public class OrderForDirector extends OrderActivity{

    ListView listView;
    List<Order> orderList;
    SQLiteDatabase database;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    SimpleCursorAdapter idAdapter;
    DatabaseHandler databaseHandler;
    SearchView searchView;
    private boolean notifyChanged;

    private List<Order> orders = new ArrayList<>();
    private RecyclerView recyclerView;
    private OrdersAdapter ordersAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_for_director);



        databaseHandler = new DatabaseHandler(getApplicationContext());
//        orderList = databaseHandler.getAllOrders();
//        ActionBar actionBar = getSupportActionBar();
//
//        database = databaseHandler.getReadableDatabase();
//        userCursor =  database.rawQuery("select * from "+ Util.TABLE_NAME, null);
//        String[] headers = new String[] {
//                Util.KEY_ID,
//                Util.KEY_FULLNAME,
//                Util.KEY_ADDRESS,
//                Util.KEY_DATE,
//                Util.KEY_FILLING,
//                Util.KEY_CONFECTIONARY_NAME,
//                Util.KEY_QUANTITY};
//         //создаем адаптер, передаем в него курсор
//        int[] to = new int[]{ R.id.id,
//                R.id.fullname,
//                R.id.address,
//                R.id.date,
//                R.id.confectionary_name,
//                R.id.quantity};
//        userAdapter = new SimpleCursorAdapter(OrderForDirector.this, R.layout.items,
//                userCursor, headers, to,0);
//        recyclerView.setAdapter();
//        registerForContextMenu(recyclerView);

        //Здесь получаем наш поиск, вид куда будем добавлять результаты и данные из базы
        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.recycler_view);
        orders = databaseHandler.getAllOrders();

        //Здесь передаем все, что получили адаптеру, его написали сами, там основная логика
        ordersAdapter = new OrdersAdapter(this, orders);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(ordersAdapter);

//        Здесь добавили реакцию на ввод в поиске, когда вводим слово, вызывается фильтр
//        он и делает двоичный поиск по заказам
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryString) {
                ordersAdapter.getFilter().filter(queryString);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryString) {
                ordersAdapter.getFilter().filter(queryString);
                return false;
            }
        });


        //Сортировка делается по дате, так как сравнение даты делает компаратор, то даты сортируются
        //как строки в лексикографическом порядке. Соответсвтенно можешь ввести заказы с датами, где
        //первые две цифры правильное число, тогда будет работать идеально. По другому надо распарсить
        //строку, но так не делают. Изначально нужно было в заказе делать дату типа Date.
        Button button = (Button) findViewById(R.id.sort);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Collections.sort(orders, new Comparator<Order>() {
                    @Override
                    public int compare(Order order1, Order order2) {
                        return order1.getDate().substring(0,1).compareTo(order2.getQuantity().substring(0,1));
                    }
                });
            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        database.close();
        userCursor.close();
    }
}
