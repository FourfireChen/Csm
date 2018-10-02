package com.chuansongmen.data;

import android.provider.ContactsContract;

public class DataRepository implements IDataRepository{
    private static IDataRepository instance;
    private DataRepository(){};

    public static IDataRepository getInstance(){
        if (instance == null)
            instance = new DataRepository();
        return instance;
    }
}
