/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.8).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.annotations.*;
import io.swagger.model.Account;
import io.swagger.model.requests.AccountRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-19T16:39:42.654Z[GMT]")
@Api(value = "Accounts", description = "the Accounts API")
public interface AccountsApi {

    @ApiOperation(value = "deletes savings or current of account, not to be used with vault", nickname = "deleteAccount", notes = "", authorizations = {
        @Authorization(value = "bearerAuth")    }, tags={ "Account", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Account deleted"),
        @ApiResponse(code = 403, message = "Forbidden") })
    @RequestMapping(value = "/Employee/Accounts",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteAccount(@NotNull @ApiParam(value = "The ID of the Account", required = true) @Valid @RequestParam(value = "id", required = true) Integer id);


    @ApiOperation(value = "calling an account", nickname = "getAccount", notes = "Calling this allows you to fetch an specific account data", response = Object.class, authorizations = {
        @Authorization(value = "bearerAuth")    }, tags={ "Account", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "the account data", response = Object.class),
        @ApiResponse(code = 400, message = "bad input parameter"),
        @ApiResponse(code = 403, message = "Forbidden") })
    @RequestMapping(value = "/Customer/Accounts/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Account> getAccount(@ApiParam(value = "the account id",required=true) @PathVariable("id") Integer id);

    /*
    @ApiOperation(value = "calling an account2", nickname = "getAccountByIban", notes = "Calling this allows you to fetch an specific account data", response = Object.class, authorizations = {
            @Authorization(value = "bearerAuth")    }, tags={ "Account", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "the account data", response = Object.class),
            @ApiResponse(code = 400, message = "bad input parameter"),
            @ApiResponse(code = 403, message = "Forbidden") })
    @RequestMapping(value = "/Accounts/{iban}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Account> getAccountByIban(@ApiParam(value = "the account iban",required=true) @PathVariable("iban") String iban);
    */

    @ApiOperation(value = "Get all accounts available on the system", nickname = "getAllAccounts", notes = "Calling this allows you to fetch the accounts data, an input parameter \"savings\", \"currents\" or \"vault\" allows you to filter through savings or currents accounts", response = Object.class, responseContainer = "List", authorizations = {
        @Authorization(value = "bearerAuth")    }, tags={ "Account", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Accounts data", response = Object.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "bad input parameter"),
        @ApiResponse(code = 403, message = "Forbidden") })
    @RequestMapping(value = "/Employee/Accounts",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Account>> getAllAccounts(@ApiParam(value = "search criteria") @Valid @RequestParam(value = "", required = false) String search);


    @ApiOperation(value = "Register a new account on the system", nickname = "registerAccount", notes = "Calling this allows you to insert a new account in db", response = Object.class, authorizations = {
        @Authorization(value = "bearerAuth")    }, tags={ "Account", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful registration", response = Object.class),
        @ApiResponse(code = 401, message = "Invalid registration"),
        @ApiResponse(code = 403, message = "Forbidden") })
    @RequestMapping(value = "/Employee/Accounts",
        produces = { "application/json" }, consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Object> registerAccount(@ApiParam(value = "type of accounts to be created") @Valid @RequestBody AccountRequest account);

}
