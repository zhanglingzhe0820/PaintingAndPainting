package com.painting.springcontroller.account;

import com.painting.entity.account.Role;
import com.painting.response.Response;
import com.painting.response.WrongResponse;
import com.painting.response.user.RequesterInfoResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize(value = "hasRole('" + Role.REQUESTER_NAME + "')")
@RestController
public class RequesterInfoController {

    @Authorization("发起者本人")
    @ApiOperation(value = "发起者个人中心", notes = "发起者获得自己的个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "发起者用户名", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = "account/requester/{username}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = RequesterInfoResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 404, message = "not a worker", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> info(@PathVariable("username") String username) {
        return null;
    }

}
