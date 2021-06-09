<#macro employee employees>
<div class="row">
    <div class="col-sm">
        <form id="createCustomer" name="createCustomer">
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label class="custom-label">Введите логин</label>
                    <input id="login" type="text" name="login" class="form-control" placeholder="Введите email"
                           value="<#if employees?has_content>${employees.email}</#if>">
                </div>
                <div class="form-group col-md-6">
                    <label class="custom-label">Введите пароль</label>
                    <input id="password" type="text" name="password" class="form-control"
                           placeholder="Введите пароль"
                           value="<#if employees?has_content && employees.password?has_content>${employees.password}</#if>">
                </div>
            </div>
        </form>
    </div>
</div>
</#macro>