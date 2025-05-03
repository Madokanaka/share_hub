<#import "../layout.ftlh" as main>

<@main.layout>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .admin-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 40px 20px;
        }

        .admin-card {
            background: linear-gradient(135deg, #ffffff, #e9ecef);
            border: none;
            border-radius: 15px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
        }

        .admin-card:hover {
            transform: translateY(-5px);
        }

        .file-card {
            border: none;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
            transition: all 0.3s ease;
            background: #ffffff;
        }

        .file-card:hover {
            box-shadow: 0 6px 18px rgba(0, 0, 0, 0.15);
            transform: translateY(-3px);
        }

        .file-card .card-body {
            padding: 20px;
        }

        .file-card .btn-action {
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }

        .btn-delete {
            background-color: #dc3545;
            border: none;
        }

        .btn-delete:hover {
            background-color: #c82333;
        }

        .btn-edit {
            background-color: #ffc107;
            border: none;
        }

        .btn-edit:hover {
            background-color: #e0a800;
        }

        .pagination-controls .btn {
            border-radius: 5px;
            padding: 10px 20px;
            font-weight: 500;
        }

        .pagination-controls .page-info {
            font-size: 1.1rem;
            color: #495057;
        }

        .fade-in {
            animation: fadeIn 0.5s ease-in;
        }

        @keyframes fadeIn {
            0% {
                opacity: 0;
                transform: translateY(20px);
            }
            100% {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .badge-public {
            background-color: #28a745;
            font-size: 0.9rem;
        }

        .badge-private {
            background-color: #dc3545;
            font-size: 0.9rem;
        }

        .modal-content {
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        }

        .modal-header {
            background: linear-gradient(135deg, #007bff, #0056b3);
            color: #ffffff;
            border-top-left-radius: 10px;
            border-top-right-radius: 10px;
        }

        .modal-body {
            background-color: #ffffff;
            padding: 20px;
        }

        .btn-upload {
            background-color: #28a745;
            border: none;
            transition: background-color 0.3s ease;
        }

        .btn-upload:hover {
            background-color: #218838;
        }

        .alert-success {
            background-color: #d4edda;
            border-color: #c3e6cb;
            color: #155724;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }

        .alert-error {
            background-color: #f8d7da;
            border-color: #f5c6cb;
            color: #721c24;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
    </style>

    <div class="admin-container">
        <h1 class="text-center mb-5 fade-in" style="color: #343a40;">Админ-панель</h1>

        <div class="row justify-content-between mb-4">
            <div class="col-md-4">
                <div class="card admin-card fade-in">
                    <div class="card-body text-center">
                        <h5 class="card-title" style="color: #007bff;">Управление файлами</h5>
                        <p class="card-text">Контролируйте файлы и категории</p>
                    </div>
                </div>
            </div>
        </div>


        <#if success??>
            <div class="alert alert-success fade-in" role="alert">
                <i class="fas fa-check-circle me-2"></i>${success}
            </div>
        </#if>
        <#if error??>
            <div class="alert alert-error fade-in" role="alert">
                <i class="fas fa-exclamation-circle me-2"></i>${error}
            </div>
        </#if>

        <div class="mb-4">
            <h4>Фильтровать по категории</h4>
            <form action="/admin" method="get">
                <div class="form-group">
                    <select name="categoryId" class="form-select" onchange="this.form.submit()">
                        <option value="">Все категории</option>
                        <#list categories as category>
                            <option value="${category.id}" <#if selectedCategoryId?? && category.id == selectedCategoryId>selected</#if>>${category.name}</option>
                        </#list>
                    </select>
                </div>
            </form>
        </div>

        <h3 class="mb-4 fade-in" style="color: #343a40;">Все файлы</h3>
        <#if files?has_content>
            <div class="row">
                <#list files as file>
                    <div class="col-md-4 mb-4 fade-in">
                        <div class="card file-card h-100">
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title">
                                    <i class="fas fa-file-alt me-2"></i>${file.filename}
                                </h5>
                                <p class="card-text">
                                    <i class="fas fa-calendar-alt me-2"></i>Загружено: ${file.uploadDate}
                                </p>
                                <p class="card-text">
                                    <i class="fas fa-folder-open me-2"></i>Категория: ${file.categoryName!"Без категории"}
                                </p>
                                <p class="card-text">
                                    <i class="fa-solid fa-download me-2"></i>Скачивания: ${file.downloadCount!"0"}
                                </p>
                                <p class="card-text">
                                    <span class="badge ${file.isPublic()?then('badge-public', 'badge-private')}">
                                        ${file.isPublic()?then('Публичный', 'Приватный')}
                                    </span>
                                </p>
                                <div class="mt-auto">
                                    <div class="row row-cols-2 justify-content-between">
                                        <div class="col d-flex align-items-stretch">
                                            <form method="post" action="/admin/delete/${file.id}" class="w-100">
                                                <#if _csrf??>
                                                    <input type="hidden"
                                                           name="${(_csrf.parameterName)!'csrf-param-name'}"
                                                           value="${(_csrf.token)!'csrf-token'}"/>
                                                </#if>
                                                <button type="submit" class="btn btn-delete btn-action w-100">
                                                    <i class="fas fa-trash me-2"></i>Удалить
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        <#else>
            <div class="alert alert-warning fade-in" role="alert">
                <i class="fas fa-exclamation-circle me-2"></i>Файлы не найдены.
            </div>
        </#if>

        <div class="pagination-controls d-flex justify-content-between align-items-center mt-4 fade-in">
            <div>
                <#if (currentPage > 0)>
                    <a href="?page=${currentPage - 1}&categoryId=${selectedCategoryId!''}" class="btn btn-outline-primary">
                        <i class="fas fa-chevron-left me-2"></i>Предыдущая
                    </a>
                </#if>
            </div>
            <div class="page-info">
                Страница ${currentPage + 1} из ${totalPages}
            </div>
            <div>
                <#if (currentPage < totalPages - 1)>
                    <a href="?page=${currentPage + 1}&categoryId=${selectedCategoryId!''}" class="btn btn-outline-primary">
                        Следующая<i class="fas fa-chevron-right ms-2"></i>
                    </a>
                </#if>
            </div>
        </div>
    </div>

</@main.layout>