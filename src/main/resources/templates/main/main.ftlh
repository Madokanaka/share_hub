<#import "../layout.ftlh" as main>

<@main.layout>
    <div class="container mt-5">
        <h1 class="text-center mb-4">Файлы</h1>

        <div class="mb-4">
            <h4>Фильтровать по категории</h4>
            <form action="/" method="get">
                <div class="form-group">
                    <select name="categoryId" class="form-select" onchange="this.form.submit()">
                        <#list categories as category>
                            <option value="${category.id}" <#if (category.id == selectedCategoryId) && (category.id == selectedCategoryId)>selected</#if>>${category.name}</option>
                        </#list>
                    </select>
                </div>
            </form>
        </div>

        <#if files?has_content>
            <div class="row">
                <#list files as file>
                    <div class="col-md-4 mb-4">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">${file.filename}</h5>
                                <p class="card-text">Загрузил: ${file.ownerEmail}</p>
                                <p class="card-text">Категория: ${file.categoryName}</p>
                                <p class="card-text">Скачивания: ${file.downloadCount}</p>
                                <a href="/download/${file.id}" class="btn btn-primary">Скачать</a>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        <#else>
            <div class="alert alert-warning" role="alert">
                Нет доступных файлов для отображения.
            </div>
        </#if>

        <div class="d-flex justify-content-between">
            <div>
                <#if (currentPage > 0)>
                    <a href="?page=${currentPage - 1}&categoryId=${selectedCategoryId}" class="btn btn-link">Предыдущая</a>
                </#if>
            </div>
            <div>
                <span>Страница ${currentPage + 1} из ${totalPages}</span>
            </div>
            <div>
                <#if (currentPage < totalPages - 1)>
                    <a href="?page=${currentPage + 1}&categoryId=${selectedCategoryId}" class="btn btn-link">Следующая</a>
                </#if>
            </div>
        </div>
    </div>
</@main.layout>
