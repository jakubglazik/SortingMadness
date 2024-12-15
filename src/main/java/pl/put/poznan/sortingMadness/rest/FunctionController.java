package pl.put.poznan.sortingMadness.rest;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/method")
public class FunctionController {
    private static final Logger logger = LoggerFactory.getLogger(SortController.class);

    private static <T> Comparator<T> getComparator(List<T> data) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("The data list is null or empty.");
        }

        T firstElement = data.get(0);

        if (firstElement instanceof Integer) {
            return (Comparator<T>) Comparator.naturalOrder();
        } else if (firstElement instanceof String) {
            return (Comparator<T>) Comparator.naturalOrder();
        } else if (firstElement instanceof Double) {
            return (Comparator<T>) Comparator.naturalOrder();
        } else if (firstElement instanceof Float) {
            return (Comparator<T>) Comparator.naturalOrder();
        } else if (firstElement instanceof Long) {
            return (Comparator<T>) Comparator.naturalOrder();
        } else if (firstElement instanceof Comparable) {
            // If the object implements Comparable, use its compareTo method
            return (o1, o2) -> ((Comparable<T>) o1).compareTo(o2);
        } else {
            throw new IllegalArgumentException("Unsupported data type: " + firstElement.getClass().getName());
        }
    }



    @PostMapping
    public Map<String, Object> invokeMethod(@RequestBody FunctionRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Load the class
            Class<?> clazz = Class.forName(request.getClassName());

            // Create an instance if the method is not static
            Object target = request.isStaticMethod() ? null : clazz.getDeclaredConstructor().newInstance();

            // Get the method
            Method method = clazz.getDeclaredMethod(request.getMethodName(), request.getParamTypes());
            method.setAccessible(true);

            // Handle dynamic comparator creation if needed
            Object[] params = request.getParams();
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    if (request.getParamTypes()[i].equals(Comparator.class)) {
                        // Assuming the previous parameter is a list to determine the comparator type
                        if (i > 0 && params[i - 1] instanceof List) {
                            @SuppressWarnings("unchecked")
                            List<Object> data = (List<Object>) params[i - 1];
                            params[i] = getComparator(data);
                        } else {
                            throw new IllegalArgumentException("Unable to determine comparator: Provide a valid data list before the comparator parameter.");
                        }
                    }
                }
            }

            // Invoke the method
            Object result = method.invoke(target, params);

            // Build success response
            response.put("status", "success");
            response.put("result", result);
        } catch (Exception e) {
            // Log the error and prepare error response
            logger.error("An error occurred: {}", e.getMessage(), e);

            Throwable cause = e.getCause();
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", cause != null ? cause.getClass().getName() + ": " + cause.getMessage() : e.getClass().getName() + ": " + e.getMessage());
        }
        return response;
    }


}
