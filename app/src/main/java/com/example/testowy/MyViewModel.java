package com.example.testowy;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel
{
    private MutableLiveData<String> currentUrl = new MutableLiveData<>();
    private MutableLiveData<String> currentMimeType = new MutableLiveData<>();

    public MyViewModel()
    {

    }

    public void selectUrl(String url)
    {
        currentUrl.postValue(url);
    }

    public void selectMimeType(String mimeType)
    {
        currentMimeType.postValue(mimeType);
    }

    public MutableLiveData<String> getCurrentUrl()
    {
        return currentUrl;
    }

    public MutableLiveData<String> getCurrentMimeType()
    {
        return currentMimeType;
    }
}
