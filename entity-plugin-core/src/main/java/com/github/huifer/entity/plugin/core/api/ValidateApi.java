package com.github.huifer.entity.plugin.core.api;

public interface ValidateApi<InsType, UpType> {

    /**
     * validate for inserted param
     *
     * @param insType insert param
     */
    void validateInsType(InsType insType);

    /**
     * validate for updated param
     *
     * @param upType update param
     */
    void validateUpType(UpType upType);

}
