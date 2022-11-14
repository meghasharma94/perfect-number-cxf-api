package com.number.controller;

import com.number.exception.InvalidRequestException;
import com.number.service.PerfectNumberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/perfect-numbers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PerfectNumberController {


    private PerfectNumberService perfectNumberService = new PerfectNumberService();

    @GET
    @Path("/validate/{number}")
    @Operation(summary = "This api checks if the requested number is a perfect number or not.",
            tags = {"perfectNumber"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Request processed"),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad input")})
    public Response validateNumber(@PathParam("number") Integer number) {
        if (number == null) {
            throw new InvalidRequestException("Invalid requested parameters");
        }
        boolean result=perfectNumberService.checkForPerfectNumber(number);
        return  Response.status(Response.Status.OK)
                .entity(result)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @GET
    @Path("/listAllPerfectNumbers")
    @Operation(summary = "This api lists all the perfect number in the given range",
            tags = {"perfectNumber"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Request processed"),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad input")})
    public Response listAllPerfectNumbers( @QueryParam("from") Integer from,  @QueryParam("to") Integer to) {
        if (from == null || to == null) {
            throw new InvalidRequestException("Invalid requested parameters; either of the passed query param is null");
        }
        List<Integer> perfectNumberList=perfectNumberService.listAllPerfectNumber(from, to);
        return Response.status(Response.Status.OK)
                .entity(perfectNumberList)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}