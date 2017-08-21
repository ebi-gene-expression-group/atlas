package uk.ac.ebi.atlas.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope("request")
public class RequestDiagnosticsController extends HtmlExceptionHandlingController {

    @RequestMapping(value = "/request-diagnostics", produces = "text/html;charset=UTF-8")
    public String showRequestDiagnosticsPage() {
        return "request-diagnostics";
    }

}