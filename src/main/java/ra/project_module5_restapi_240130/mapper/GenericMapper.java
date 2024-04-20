package ra.project_module5_restapi_240130.mapper;

public interface GenericMapper<E,R,S> {
    E mapperRequestToEntity(R r);
    S mapperEntityToResponse(E e);
}
