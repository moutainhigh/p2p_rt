
/**
 * IdentifierServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.rbao.east.checkrealname;

    /**
     *  IdentifierServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class IdentifierServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public IdentifierServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public IdentifierServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for querySimpleCitizenData method
            * override this method for handling normal response from querySimpleCitizenData operation
            */
           public void receiveResultquerySimpleCitizenData(
                    com.rbao.east.checkrealname.IdentifierServiceStub.QuerySimpleCitizenDataResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from querySimpleCitizenData operation
           */
            public void receiveErrorquerySimpleCitizenData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for simpleCheck method
            * override this method for handling normal response from simpleCheck operation
            */
           public void receiveResultsimpleCheck(
                    com.rbao.east.checkrealname.IdentifierServiceStub.SimpleCheckResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from simpleCheck operation
           */
            public void receiveErrorsimpleCheck(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for nciicAddrExactSearch method
            * override this method for handling normal response from nciicAddrExactSearch operation
            */
           public void receiveResultnciicAddrExactSearch(
                    com.rbao.east.checkrealname.IdentifierServiceStub.NciicAddrExactSearchResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from nciicAddrExactSearch operation
           */
            public void receiveErrornciicAddrExactSearch(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for simpleCheckByJson method
            * override this method for handling normal response from simpleCheckByJson operation
            */
           public void receiveResultsimpleCheckByJson(
                    com.rbao.east.checkrealname.IdentifierServiceStub.SimpleCheckByJsonResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from simpleCheckByJson operation
           */
            public void receiveErrorsimpleCheckByJson(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for queryBalance method
            * override this method for handling normal response from queryBalance operation
            */
           public void receiveResultqueryBalance(
                    com.rbao.east.checkrealname.IdentifierServiceStub.QueryBalanceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from queryBalance operation
           */
            public void receiveErrorqueryBalance(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modifyPasswordByJson method
            * override this method for handling normal response from modifyPasswordByJson operation
            */
           public void receiveResultmodifyPasswordByJson(
                    com.rbao.east.checkrealname.IdentifierServiceStub.ModifyPasswordByJsonResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modifyPasswordByJson operation
           */
            public void receiveErrormodifyPasswordByJson(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for queryExactCitizenData method
            * override this method for handling normal response from queryExactCitizenData operation
            */
           public void receiveResultqueryExactCitizenData(
                    com.rbao.east.checkrealname.IdentifierServiceStub.QueryExactCitizenDataResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from queryExactCitizenData operation
           */
            public void receiveErrorqueryExactCitizenData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for isExactCitizenExists method
            * override this method for handling normal response from isExactCitizenExists operation
            */
           public void receiveResultisExactCitizenExists(
                    com.rbao.east.checkrealname.IdentifierServiceStub.IsExactCitizenExistsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from isExactCitizenExists operation
           */
            public void receiveErrorisExactCitizenExists(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for batchExactCheck method
            * override this method for handling normal response from batchExactCheck operation
            */
           public void receiveResultbatchExactCheck(
                    com.rbao.east.checkrealname.IdentifierServiceStub.BatchExactCheckResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from batchExactCheck operation
           */
            public void receiveErrorbatchExactCheck(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modifyPassword method
            * override this method for handling normal response from modifyPassword operation
            */
           public void receiveResultmodifyPassword(
                    com.rbao.east.checkrealname.IdentifierServiceStub.ModifyPasswordResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modifyPassword operation
           */
            public void receiveErrormodifyPassword(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for queryExactHistoryData method
            * override this method for handling normal response from queryExactHistoryData operation
            */
           public void receiveResultqueryExactHistoryData(
                    com.rbao.east.checkrealname.IdentifierServiceStub.QueryExactHistoryDataResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from queryExactHistoryData operation
           */
            public void receiveErrorqueryExactHistoryData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createUser method
            * override this method for handling normal response from createUser operation
            */
           public void receiveResultcreateUser(
                    com.rbao.east.checkrealname.IdentifierServiceStub.CreateUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createUser operation
           */
            public void receiveErrorcreateUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for exactCheckByJson method
            * override this method for handling normal response from exactCheckByJson operation
            */
           public void receiveResultexactCheckByJson(
                    com.rbao.east.checkrealname.IdentifierServiceStub.ExactCheckByJsonResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from exactCheckByJson operation
           */
            public void receiveErrorexactCheckByJson(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for queryUser method
            * override this method for handling normal response from queryUser operation
            */
           public void receiveResultqueryUser(
                    com.rbao.east.checkrealname.IdentifierServiceStub.QueryUserResponse0 result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from queryUser operation
           */
            public void receiveErrorqueryUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for queryExactHistoryDataCount method
            * override this method for handling normal response from queryExactHistoryDataCount operation
            */
           public void receiveResultqueryExactHistoryDataCount(
                    com.rbao.east.checkrealname.IdentifierServiceStub.QueryExactHistoryDataCountResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from queryExactHistoryDataCount operation
           */
            public void receiveErrorqueryExactHistoryDataCount(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for queryAllUser method
            * override this method for handling normal response from queryAllUser operation
            */
           public void receiveResultqueryAllUser(
                    com.rbao.east.checkrealname.IdentifierServiceStub.QueryAllUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from queryAllUser operation
           */
            public void receiveErrorqueryAllUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for querySimpleHistoryData method
            * override this method for handling normal response from querySimpleHistoryData operation
            */
           public void receiveResultquerySimpleHistoryData(
                    com.rbao.east.checkrealname.IdentifierServiceStub.QuerySimpleHistoryDataResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from querySimpleHistoryData operation
           */
            public void receiveErrorquerySimpleHistoryData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for querySimpleHistoryDataCount method
            * override this method for handling normal response from querySimpleHistoryDataCount operation
            */
           public void receiveResultquerySimpleHistoryDataCount(
                    com.rbao.east.checkrealname.IdentifierServiceStub.QuerySimpleHistoryDataCountResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from querySimpleHistoryDataCount operation
           */
            public void receiveErrorquerySimpleHistoryDataCount(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for login method
            * override this method for handling normal response from login operation
            */
           public void receiveResultlogin(
                    com.rbao.east.checkrealname.IdentifierServiceStub.LoginResponse1 result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from login operation
           */
            public void receiveErrorlogin(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modifyUser method
            * override this method for handling normal response from modifyUser operation
            */
           public void receiveResultmodifyUser(
                    com.rbao.east.checkrealname.IdentifierServiceStub.ModifyUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modifyUser operation
           */
            public void receiveErrormodifyUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for exactCheck method
            * override this method for handling normal response from exactCheck operation
            */
           public void receiveResultexactCheck(
                    com.rbao.east.checkrealname.IdentifierServiceStub.ExactCheckResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from exactCheck operation
           */
            public void receiveErrorexactCheck(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for allocateFund method
            * override this method for handling normal response from allocateFund operation
            */
           public void receiveResultallocateFund(
                    com.rbao.east.checkrealname.IdentifierServiceStub.AllocateFundResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from allocateFund operation
           */
            public void receiveErrorallocateFund(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for isSimpleCitizenExists method
            * override this method for handling normal response from isSimpleCitizenExists operation
            */
           public void receiveResultisSimpleCitizenExists(
                    com.rbao.east.checkrealname.IdentifierServiceStub.IsSimpleCitizenExistsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from isSimpleCitizenExists operation
           */
            public void receiveErrorisSimpleCitizenExists(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getVersion method
            * override this method for handling normal response from getVersion operation
            */
           public void receiveResultgetVersion(
                    com.rbao.east.checkrealname.IdentifierServiceStub.GetVersionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getVersion operation
           */
            public void receiveErrorgetVersion(java.lang.Exception e) {
            }
                


    }
    