// Daniel Munoz, William Brewster, Mikenzie Adkins.
// DataBase.DB_Constants version: 1.0
// Date Modified: 4/3/2026

package com.capstoneshipping.DataBase;

// DB_Constants creates all static final constants for DataBase Package.
public class DB_Constants {

    
    // TABLE_NAMES
    public static final String CUSTOMER_TABLE           = "Customer";
    public static final String CUSTOMER_ADDRESS_TABLE   = "Customer_Address";
    public static final String PRODUCT_TABLE            = "Product";
    public static final String INVENTORY_TABLE          = "Inventory";
    public static final String SHOPPING_CART_TABLE      = "Shopping_Cart";
    public static final String SHOPPING_CART_ITEM_TABLE = "Shopping_Cart_Item";
    public static final String ORDER_TABLE              = "`Order`";
    public static final String ORDER_ITEM_TABLE         = "Order_Item";
    public static final String PAYMENT_TABLE            = "Payment";
    public static final String DISCOUNT_TABLE           = "Discount";
    public static final String SHIPPING_TABLE           = "Shipping";


    // CUSTOMER_COLUMNS
    public static final String CUSTOMER_ID         = "Customer_ID";
    public static final String CUSTOMER_MEMBERSHIP = "Membership_Level";
    public static final String CUSTOMER_FIRST_NAME = "first_name";
    public static final String CUSTOMER_LAST_NAME  = "last_name";
    public static final String CUSTOMER_EMAIL      = "email";


    // CUSTOMER_ADDRESS_COLUMNS
    public static final String ADDRESS_ID          = "Address_ID";
    public static final String ADDRESS_CUSTOMER_ID = "Customer_ID";
    public static final String ADDRESS_STREET      = "street";
    public static final String ADDRESS_CITY        = "city";
    public static final String ADDRESS_POSTAL      = "postal";
    
    
    // PRODUCT_COLUMNS
    public static final String PRODUCT_ID          = "Product_ID";
    public static final String PRODUCT_CATEGORY_ID = "Category_ID";
    public static final String PRODUCT_SUPPLIER_ID = "Supplier_ID";
    public static final String PRODUCT_NAME        = "product_name";


    // INVENTORY_COLUMNS
    public static final String INVENTORY_ID          = "Inventory_ID";
    public static final String INVENTORY_PRODUCT_ID  = "Product_ID";
    public static final String INVENTORY_QTY_ON_HAND = "qty_on_hand";
    public static final String INVENTORY_PRICE       = "price";


    // SHOPPING_CART_COLUMNS
    public static final String SHOPPING_CART_ID          = "Cart_ID";
    public static final String SHOPPING_CART_CUSTOMER_ID = "Customer_ID";
    public static final String SHOPPING_CART_CREATED_ART = "created_at";


    // ORDER_COLUMNS
    public static final String ORDER_ID          = "Order_ID";
    public static final String ORDER_CUSTOMER_ID = "Customer_ID";
    public static final String ORDER_DATE        = "order_date";


    // ORDER_ITEM_COLUMNS
    public static final String ORDER_ITEM_ORDER_ID     = "Order_ID";
    public static final String ORDER_ITEM_INVENTORY_ID = "Inventory_ID";
    public static final String ORDER_ITEM_QTY          = "qty";
    public static final String ORDER_ITEM_AMOUNT       = "amount";
    public static final String ORDER_ITEM_TAX          = "tax";
    

    // PAYMENT_COLUMNS
    public static final String PAYMENT_ID       = "Payment_ID";
    public static final String PAYMENT_ORDER_ID = "Order_ID";
    public static final String PAYMENT_METHOD   = "method";
    public static final String PAYMENT_STATUS   = "payment_status";


    // SHIPPING_COLUMNS
    public static final String SHIPPING_ID                 = "Shipping_ID";
    public static final String SHIPPING_ORDER_ID           = "Order_ID";
    public static final String SHIPPING_ADDRESS_ID         = "Shipping_Address_ID";
    public static final String SHIPPING_BILLING_ADDRESS_ID = "Billing_Address_ID";
    public static final String SHIPPING_CARRIER            = "carrier";
    public static final String SHIPPING_TRACKING           = "tracking";
    

    // DISCOUNT_COLUMNS
    public static final String DISCOUNT_ID         = "Discount_ID";
    public static final String DISCOUNT_PRODUCT_ID = "Product_ID";
    public static final String DISCOUNT_ORDER_ID   = "Order_ID";
    public static final String DISCOUNT_TYPE       = "type";
    public static final String DISCOUNT_AMOUNT     = "amount";
    public static final String DISCOUNT_START_DATE = "start_date";
    public static final String DISCOUNT_END_DATE   = "end_date";

}
