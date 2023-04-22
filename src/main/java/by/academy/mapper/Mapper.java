package by.academy.mapper;

public interface Mapper<T, R> {

    T mapToEntity(R object);

    R mapToDTO(T object);

    T map(R fromObject, T toObject);
}
