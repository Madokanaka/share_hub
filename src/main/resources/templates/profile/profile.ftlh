<#import "../layout.ftlh" as main>

<@main.layout>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .profile-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 40px 20px;
        }

        .profile-card {
            background: linear-gradient(135deg, #ffffff, #e9ecef);
            border: none;
            border-radius: 15px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
        }

        .profile-card:hover {
            transform: translateY(-5px);
        }

        .profile-card img {
            border-radius: 50%;
            width: 120px;
            height: 120px;
            object-fit: cover;
            margin: 0 auto;
            border: 3px solid #007bff;
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

        .file-card .btn-download {
            background-color: #007bff;
            border: none;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }

        .file-card .btn-download:hover {
            background-color: #0056b3;
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

        .alert-link {
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

    <div class="profile-container">
        <h1 class="text-center mb-5 fade-in" style="color: #343a40;">Профиль пользователя</h1>

        <div class="row justify-content-between">
            <div class="col-md-4 mb-4">
                <div class="card profile-card fade-in">
                    <div class="card-body text-center">
                        <img src='https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png'
                             alt="User Avatar" class="border rounded-circle mb-3"
                             style="width: 100px; height: 100px; object-fit: cover;">
                        <h5 class="card-title" style="color: #007bff;">${userProfile.email}</h5>
                        <p class="card-text"><i class="fas fa-envelope me-2"></i>Email: ${userProfile.email}</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3 mb-4">
                <button type="button" class="btn btn-success btn-upload" data-bs-toggle="modal"
                        data-bs-target="#uploadModal">
                    <i class="fas fa-upload me-2"></i>Загрузить файл
                </button>
            </div>
        </div>

        <#if downloadLink??>
            <div class="alert alert-link fade-in" role="alert">
                <i class="fas fa-link me-2"></i>Ссылка для скачивания:
                <a href="${downloadLink}" class="alert-link">${downloadLink}</a>
                <button class="btn btn-sm btn-outline-secondary ms-2"
                        onclick="navigator.clipboard.writeText('${downloadLink}')">
                    <i class="fas fa-copy"></i> Копировать
                </button>
            </div>
        </#if>
        <#if error??>
            <div class="alert alert-error fade-in" role="alert">
                <i class="fas fa-exclamation-circle me-2"></i>${error}
            </div>
        </#if>

        <h3 class="mb-4 fade-in" style="color: #343a40;">Загруженные файлы</h3>
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
                                    <i class="fa-solid fa-download me-2"></i>Скачивания: ${file.downloadCount!"Без категории"}
                                </p>
                                <div class="mt-auto">
                                    <p class="card-text">
                                            <span class="badge ${file.isPublic()?then('badge-public', 'badge-private')}">
                                                ${file.isPublic()?then('Публичный', 'Приватный')}
                                            </span>
                                    </p>
                                    <div class="row row-cols-2 justify-content-between">
                                        <div class="col d-flex align-items-stretch">
                                            <a href="/download/${file.id}" class="btn btn-primary btn-download w-100">
                                                <i class="fas fa-download me-2"></i>Скачать
                                            </a>
                                        </div>
                                        <div class="col d-flex align-items-stretch">
                                            <#if (!file.isPublic())>
                                                <form method="post" action="/profile/createDownloadLink" class="ms-2 w-100">
                                                    <#if _csrf??>
                                                        <input type="hidden"
                                                               name="${(_csrf.parameterName)!'csrf-param-name'}"
                                                               value="${(_csrf.token)!'csrf-token'}"/>
                                                    </#if>
                                                    <input type="hidden" name="fileId" value="${file.id}">
                                                    <button type="submit" class="btn btn-warning btn-link-generate w-100">
                                                        <i class="fas fa-link me-2"></i>Ссылка
                                                    </button>
                                                </form>
                                            </#if>
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
                <i class="fas fa-exclamation-circle me-2"></i>У вас нет загруженных файлов.
            </div>
        </#if>

        <div class="pagination-controls d-flex justify-content-between align-items-center mt-4 fade-in">
            <div>
                <#if (currentPage > 0)>
                    <a href="?page=${currentPage - 1}" class="btn btn-outline-primary">
                        <i class="fas fa-chevron-left me-2"></i>Предыдущая
                    </a>
                </#if>
            </div>
            <div class="page-info">
                Страница ${currentPage + 1} из ${totalPages}
            </div>
            <div>
                <#if (currentPage < totalPages - 1)>
                    <a href="?page=${currentPage + 1}" class="btn btn-outline-primary">
                        Следующая<i class="fas fa-chevron-right ms-2"></i>
                    </a>
                </#if>
            </div>
        </div>
    </div>
    <div class="modal fade" id="uploadModal" tabindex="-1" aria-labelledby="uploadModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="uploadModalLabel"><i class="fas fa-upload me-2"></i>Загрузка файла</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form method="post" enctype="multipart/form-data" action="/profile/upload">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                   value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>
                        <div class="mb-3">
                            <label class="form-label">Выберите файл</label>
                            <input type="file" name="file" class="form-control" required>
                        </div>
                        勞
                        <div class="mb-3">
                            <label class="form-label">Категория</label>
                            <select name="categoryId" class="form-select" required>
                                <#list categories as category>
                                    <option value="${category.id}">${category.name}</option>
                                </#list>
                            </select>
                        </div>
                        <div class="form-check mb-3">
                            <input class="form-check-input" type="checkbox" name="isPublic" id="isPublic">
                            <label class="form-check-label" for="isPublic">Публичный файл</label>
                        </div>
                        <button type="submit" class="btn btn-success btn-upload">Загрузить</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</@main.layout>