package truckmanagementproject.util;


public interface ValidationUtil {

    <T> boolean isValid(T entity);

    <T> String violations(T entity);
}
