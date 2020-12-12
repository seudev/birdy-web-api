package com.seudev.birdy.module.api;

import java.util.Optional;

public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Optional<Class<?>> entityClass;

    private final Optional<String> entityName;

    private final Optional<Object> entityId;

    public EntityNotFoundException(Class<?> entityClass, Object entityId) {
        this(entityClass, entityId, null);
    }

    public EntityNotFoundException(String entityName, Object entityId) {
        this(entityName, entityId, null);
    }

    public EntityNotFoundException(String entityName, Object entityId, Throwable cause) {
        super(String.format("Not found %s: %s", entityName, entityId), cause);
        this.entityClass = Optional.empty();
        this.entityName = Optional.of(entityName);
        this.entityId = Optional.ofNullable(entityId);
    }

    public EntityNotFoundException(Class<?> entityClass, Object entityId, Throwable cause) {
        super(String.format("Not found %s: %s", entityClass.getSimpleName(), entityId), cause);
        this.entityClass = Optional.of(entityClass);
        this.entityName = Optional.of(entityClass.getSimpleName());
        this.entityId = Optional.ofNullable(entityId);
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
        this.entityClass = Optional.empty();
        this.entityName = Optional.empty();
        this.entityId = Optional.empty();
    }

    public Optional<Class<?>> getEntityClass() {
        return entityClass;
    }

    public Optional<String> getEntityName() {
        return entityName;
    }

    public Optional<Object> getEntityId() {
        return entityId;
    }

}
