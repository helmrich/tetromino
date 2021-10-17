package de.hdm_stuttgart.mi.datamanager;

public interface DataManager<T> {

    void saveData(T data);

    T loadData();
    
}
