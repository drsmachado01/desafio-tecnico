package br.com.darlan.teste.cnabapi.utils.mapper;

import java.util.List;

public interface ObjectMapper<D, E> {
	D fromEntity(E entity);
	
	List<D> fromEntityList(List<E> entities);
}
