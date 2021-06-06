package ztp.edziennik.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class ServiceUtils {
    public static Pageable setPageableWithSort(int page, int size, String sort, String dir) {
        Pageable pageable;
        if (!sort.isBlank() && dir.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        } else if (!sort.isBlank() && dir.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else pageable = PageRequest.of(page, size);
        return pageable;
    }
}
