<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <div class="d-flex align-items-center justify-content-center min-vh-100">
        <div class="col-7">
            <h2 class="text-center mb-4">Регистрация</h2>
            <form action="/auth/register" method="post" enctype="multipart/form-data">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                           value="${(_csrf.token)!'csrf-token'}"/>
                </#if>
                <div class="form-group mb-3">
                    <label class="form-label" for="form3Example4cg">Email</label>
                    <@spring.formInput "userDto.email" 'id="form3Example4cg" class="form-control"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>
                <div class="form-group mb-3">
                    <label class="form-label" for="form3Example6cg">Пароль</label>
                    <@spring.formInput "userDto.password" 'id="form3Example6cg" class="form-control"' />
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>
                <#if error??>
                    <div class="text-danger mb-3">${error}</div>
                </#if>
                <button type="submit" class="btn btn-primary w-100">Зарегистрироваться</button>
            </form>
        </div>
    </div>
</@main.layout>
