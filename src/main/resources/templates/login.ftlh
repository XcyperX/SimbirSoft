<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<#import "parts/createEmployee.ftlh" as сс>

<@c.page>
    <div class="container-fluid">
        <form class="form" action="/login" method="post">
            <div class="row">
                <div class="col-md-4">
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label for="username">
                            Логин
                        </label>
                        <input type="text" id="username" name="username" class="form-control"
                               placeholder="Введите email" required autofocus>
                    </div>
                    <div class="form-group">
                        <label for="password">
                            Пароль
                        </label>
                        <input type="password" id="password" name="password" class="form-control"
                               placeholder="Введите пароль" required>
                    </div>
                    <input class="btn btn-lg btn-dark btn-block mb-4" type="submit" value="Войти">
                    <div class="form-group mb-0">
                        <label class="mb-0">
                            Регистрация:
                        </label>
                    </div>
                    <button type="button" class="btn btn-lg btn-primary btn-block mb-2" data-toggle="modal"
                            data-target="#createCustomer">
                        Регистрация
                    </button>
                    <!-- Modal -->
                    <div class="modal fade" id="createCustomer" tabindex="-1" role="dialog"
                         aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Регистрация покупателя</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <@сс.employee employees=""/>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Отмена</button>
                                    <button type="button" class="btn btn-primary"
                                            onclick="submitNewUser(this)">Регистрация
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                </div>
            </div>
        </form>
    </div>
</@c.page>

