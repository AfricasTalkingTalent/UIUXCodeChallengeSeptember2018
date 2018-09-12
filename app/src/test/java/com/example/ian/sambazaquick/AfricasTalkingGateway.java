package com.example.ian.sambazaquick; /**********************************************************************************************************************
 * # COPYRIGHT (C) 2014 AFRICASTALKING LTD <www.africastalking.com>                                                   *
 **********************************************************************************************************************
 *AFRICAStALKING SMS GATEWAY CLASS IS A FREE SOFTWARE IE. CAN BE MODIFIED AND/OR REDISTRIBUTED                        *
 *UNDER THER TERMS OF GNU GENERAL PUBLIC LICENCES AS PUBLISHED BY THE                                                 *
 *FREE SOFTWARE FOUNDATION VERSION 3 OR ANY LATER VERSION                                                             *
 **********************************************************************************************************************
 *THE CLASS IS DISTRIBUTED ON 'AS IS' BASIS WITHOUT ANY WARRANTY, INCLUDING BUT NOT LIMITED TO                        *
 *THE IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.                      *
 *IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,             *
 *WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE        *
 *OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.                                                                       *
 **********************************************************************************************************************/

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;

import org.json.*;

class MobilePaymentB2CRecipient {
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
