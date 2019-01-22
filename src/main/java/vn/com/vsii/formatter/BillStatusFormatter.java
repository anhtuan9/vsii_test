package vn.com.vsii.formatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import vn.com.vsii.model.BillStatus;
import vn.com.vsii.service.BillStatusService;

import java.text.ParseException;
import java.util.Locale;

@Component
public class BillStatusFormatter implements Formatter<BillStatus> {

    @Autowired
    private BillStatusService billStatusService;
    @Autowired
    public BillStatusFormatter(BillStatusService billStatusService) {
        this.billStatusService = billStatusService;
    }

    @Override
    public BillStatus parse(String text, Locale locale) throws ParseException {
        return billStatusService.findById(Long.parseLong(text));
    }

    @Override
    public String print(BillStatus object, Locale locale) {
        return "[" + object.getStatus_id() + ", " +object.getName() + "]";
    }
}
