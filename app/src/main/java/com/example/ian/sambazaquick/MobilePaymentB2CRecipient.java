package com.example.ian.sambazaquick;

import org.json.JSONObject;

import java.util.HashMap;

public class MobilePaymentB2CRecipient {
    private String _phoneNumber;
    private String _currencyCode;
    private Double _amount;
    private HashMap<String, String> _metadata;

    public MobilePaymentB2CRecipient(String phoneNumber_,
                                     String currencyCode_,
                                     Double amount_) {
        _phoneNumber  = phoneNumber_;
        _currencyCode = currencyCode_;
        _amount       = amount_;
        _metadata     = new HashMap<String, String>();
    }

    public void addMetadata(String key_, String  value_) {
        _metadata.put(key_, value_);
    }

    public JSONObject toJSON() throws Exception {
        return new JSONObject()
                .put("phoneNumber", _phoneNumber)
                .put("currencyCode", _currencyCode)
                .put("amount", _amount)
                .put("metadata", _metadata);
    }
}
