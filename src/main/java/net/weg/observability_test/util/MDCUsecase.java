package net.weg.observability_test.util;

import net.weg.observability_test.enums.MDCKeys;
import org.slf4j.MDC;

import java.util.Set;

public class MDCUsecase {

    private static final String mdcKey = MDCKeys.USECASE.mdcKey();
    private MDCUsecase(){}

//    private static final ThreadLocal<Set<String>> usecaseSet = ThreadLocal.withInitial(HashSet::new);

    public static boolean push(String usecase){
        Set<String> set = SetConverter.convertStringToSet(MDC.get(mdcKey));
        boolean result = set.add(usecase);
        MDC.put(mdcKey, SetConverter.convertSetToString(set));
        return result;
    }

    public static boolean pop(String usecase){
        Set<String> set = SetConverter.convertStringToSet(MDC.get(mdcKey));
        boolean result = set.remove(usecase);
        MDC.put(mdcKey, SetConverter.convertSetToString(set));
        return result;
    }

}
