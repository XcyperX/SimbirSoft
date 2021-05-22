<#macro login user subdivision positionsNames registrationEmployee=false, registrationProvider=false, create=false>
    <div class="container pl-2 pr-2">
        <div class="row">
            <div class="col">
                <#if registrationProvider>
                <form id="createProvider" name="createProvider">
                    <#elseif registrationEmployee>
                    <form id="createUser" name="createUser">
                        <#else>
                        <form id="updateUser" name="updateUser">
                            <div class="form-row">
                                <label>Редактировать пользователя</label>
                            </div>
                            </#if>

                            <#if registrationProvider>
                                <div class="form-row mb-2">
                                    <div class="col">
                                        <input id="nameFirm" name="nameFirm" type="text" class="form-control"
                                               placeholder="Название фирмы">
                                    </div>
                                </div>
                                <div class="form-row mb-2">
                                    <div class="col">
                                        <input id="email" name="email" type="text" class="form-control" placeholder="Email">
                                    </div>
                                    <div class="col">
                                        <input id="passwordUser" name="passwordUser" type="text" class="form-control" placeholder="Пароль">
                                    </div>
                                </div>
                            </#if>

                            <#if registrationEmployee>
                                <div class="form-row mb-2">
                                    <div class="col">
                                        <input id="firstName" type="text" class="form-control" placeholder="Имя">
                                    </div>
                                    <div class="col">
                                        <input id="lastName" type="text" class="form-control" placeholder="Фамилия">
                                    </div>
                                    <div class="col">
                                        <input id="secondName" type="text" class="form-control" placeholder="Отчество">
                                    </div>
                                </div>
                                <div class="form-row mb-2">
                                    <div class="col">
                                        <input id="phone" type="text" name="phone" class="form-control"
                                               placeholder="Введите телефон"
                                               value="<#if user?has_content>${user.phone}</#if>">
                                    </div>
                                    <div class="col">
                                        <input id="date_birth" type="date" name="date_birth" class="form-control"
                                               value="<#if user?has_content>${user.dateBirth}</#if>">
                                    </div>
                                    <div class="col">
                                        <select id="genders" class="form-control">
                                            <#list genders?values as gender>
                                                <#if user?has_content>
                                                    <#if gender.getNameGender() == user.gender>
                                                        <option value="${gender}"
                                                                selected>${gender.getNameGender()}</option>
                                                    <#else>
                                                        <option value="${gender}">${gender.getNameGender()}</option>
                                                    </#if>
                                                <#else>
                                                    <option value="${gender}">${gender.getNameGender()}</option>
                                                </#if>
                                            </#list>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-row mb-2">
                                    <div class="col">
                                        <input id="email" type="text" class="form-control" placeholder="Email"
                                               value="<#if user?has_content>${user.email}</#if>">
                                    </div>
                                    <div class="col">
                                        <input id="password" type="text" class="form-control" placeholder="Пароль">
                                    </div>
                                </div>

                                <hr>
                                <div class="form-row mb-2">
                                    <div class="form-group col-md-3  px-0 mx-0">
                                        <input id="number_series" type="text" name="number_series" class="form-control"
                                               placeholder="Введите серию паспорта"
                                               value="<#if user?has_content>${user.passport.numberSeries?c}</#if>">
                                    </div>
                                    <div class="form-group col-md-3">
                                        <input id="passport_id" type="text" name="passport_id" class="form-control"
                                               placeholder="Введите номер паспорта"
                                               value="<#if user?has_content>${user.passport.passportId?c}</#if>">
                                    </div>
                                    <div class="form-group col-md-3">
                                        <input id="issued_by" type="text" name="issued_by" class="form-control"
                                               placeholder="Введите кем выдан паспорт"
                                               value="<#if user?has_content>${user.passport.issuedBy}</#if>">
                                    </div>
                                    <div class="form-group col-md-3  px-0 mx-0">
                                        <input id="date_issue" type="date" name="date_issue" class="form-control"
                                               value="<#if user?has_content>${user.passport.dateIssue}</#if>">
                                    </div>
                                </div>

                                <hr>
                                <div class="form-row mb-2">
                                    <div class="form-group col-md-3  px-0 mx-0">
                                        <input id="number_series" type="text" name="number_series" class="form-control"
                                               placeholder="Введите серию паспорта"
                                               value="<#if user?has_content>${user.passport.numberSeries?c}</#if>">
                                    </div>
                                    <div class="form-group col-md-3">
                                        <input id="passport_id" type="text" name="passport_id" class="form-control"
                                               placeholder="Введите номер паспорта"
                                               value="<#if user?has_content>${user.passport.passportId?c}</#if>">
                                    </div>
                                    <div class="form-group col-md-3">
                                        <input id="issued_by" type="text" name="issued_by" class="form-control"
                                               placeholder="Введите кем выдан паспорт"
                                               value="<#if user?has_content>${user.passport.issuedBy}</#if>">
                                    </div>
                                    <div class="form-group col-md-3  px-0 mx-0">
                                        <input id="date_issue" type="date" name="date_issue" class="form-control"
                                               value="<#if user?has_content>${user.passport.dateIssue}</#if>">
                                    </div>
                                </div>
                                <div class="form-row mb-2">
                                    <input id="number_inn" type="text" name="number_inn" class="form-control"
                                           placeholder="Введите ИНН"
                                           value="<#if user?has_content>${user.numberINN?c}</#if>">
                                </div>

                                <hr>
                                <div class="form-row mb-2">
                                    <div class="col pl-0 ml-0">
                                        <label>Выберите подразделение</label>
                                        <select id="subdivision" class="form-control">
                                            <#list subdivision as subdivision>
                                                <#if user?has_content>
                                                    <#if subdivision.id == user.subdivisionId>
                                                        <option value="${subdivision.id}"
                                                                selected>${subdivision.name}</option>
                                                    <#else>
                                                        <option value="${subdivision.id}">${subdivision.name}</option>
                                                    </#if>
                                                <#else>
                                                    <option value="${subdivision.id}">${subdivision.name}</option>
                                                </#if>
                                            </#list>
                                        </select>
                                    </div>
                                    <div class="col">
                                        <label>Выберите должность</label>
                                        <select id="positions" class="form-control">
                                            <option value="null"></option>
                                            <#list positionsNames as positions>
                                                <#if user?has_content>
                                                    <#if positions.id == user.position.positionNameId>
                                                        <option value="${positions.id}"
                                                                selected>${positions.name}</option>
                                                    <#else>
                                                        <option value="${positions.id}">${positions.name}</option>
                                                    </#if>
                                                <#else>
                                                    <option value="${positions.id}">${positions.name}</option>
                                                </#if>
                                            </#list>
                                        </select>
                                    </div>
                                    <div class="col pr-0 mr-0">
                                        <label>Уровень доступа</label>
                                        <select id="role" class="form-control">
                                            <#list roles?values as role>
                                                <option value="${role}">${role.getNameRole()}</option>
                                            </#list>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-row mb-2">
                                    <div class="col pl-0 ml-0">
                                        <label>Дата приема на работу</label>
                                        <input id="date_receipt" type="date" name="date_receipt" class="form-control"
                                               value="<#if user?has_content>${user.position.dateReceipt}</#if>">
                                    </div>
                                    <#if !create>
                                        <div class="col pr-0 mr-0">
                                            <label>Дата увольнения</label>
                                            <input id="date_dismissal" type="date" name="date_dismissal"
                                                   class="form-control"
                                                   value="<#if user?has_content && user.position.dateDismissal?has_content>${user.position.dateDismissal}</#if>">
                                        </div>
                                    </#if>
                                </div>

                                <div class="form-row mb-2">
                                    <div class="form-group col-md-6 pl-0 ml-0">
                                        <label>Дата начала отпуска</label>
                                        <input id="vacation_start" type="date" name="vacation_start"
                                               class="form-control"
                                               value="<#if user?has_content && user.vacationStart?has_content>${user.vacationStart}</#if>">
                                    </div>
                                    <div class="form-group col-md-6 pr-0 mr-0">
                                        <label>Дата окончания отпуска</label>
                                        <input id="vacation_final" type="date" name="vacation_final"
                                               class="form-control"
                                               value="<#if user?has_content && user.vacationFinal?has_content>${user.vacationFinal}</#if>">
                                    </div>
                                </div>
                            </#if>
                        </form>
            </div>
        </div>
    </div>
</#macro>

