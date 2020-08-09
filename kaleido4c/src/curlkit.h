/***********
 * Copyright (C) 2018, Tenderlies.
 * 
 * This is a http client that wraps curl. Something like a RESTful http client.
 */
#ifndef CURLKIT_H_
#define CURLKIT_H_

#include <curl/curl.h>
#include <string.h>
#include <map>
#include <list>
#include "utils.h"

using std::list;
using std::map;
using std::string;

class CurlKit {
private:
    struct MemData {
        char *data;
        size_t size;
    };

    CURL *curl_;
    CURLcode res_;
    struct curl_slist *headerList_ = nullptr;
    struct CurlKit::MemData *chunk_;

    static size_t ReadMemoryCallback(void *ptr, size_t size, size_t nmemb, void *stream) {
        char *data = (char *) stream;
        memcpy(ptr, data, size * nmemb);
        return size * nmemb;
    };

    static size_t WriteMemoryCallback(void *ptr, size_t size, size_t nmemb, void *stream) {
        size_t realSize = size * nmemb;
        if (realSize == 0) {
            return realSize;
        }

        struct CurlKit::MemData *mem = (struct CurlKit::MemData *) stream;

        mem->data = (char *) realloc(mem->data, mem->size + realSize + 1);
        if (mem->data == nullptr) {
            return 0;
        }

        memcpy(&(mem->data[mem->size]), ptr, realSize);
        mem->size += realSize;
        mem->data[mem->size] = 0;
        return realSize;
    };

    static size_t ReadFileCallback(void *ptr, size_t size, size_t nmemb, void *stream) {
        size_t retcode = fread(ptr, size, nmemb, (FILE *) stream);
        return retcode;
    };

    static size_t WriteFileCallback(void *ptr, size_t size, size_t nmemb, void *stream) {
        size_t retcode = fwrite(ptr, size, nmemb, (FILE *) stream);
        return retcode;
    };

    static size_t multiWriteFileCallback(void *ptr, size_t size, size_t nmemb, void *stream) {
        char *filePath = (char *) stream;
        FILE *file;
        fopen_s(&file, filePath, "ab");
        size_t retcode = fwrite(ptr, size, nmemb, file);
        fclose(file);
        return retcode;
    };

public:
    struct SslPem {
        char *sslcert_path;
        char *sslcert_pwd;
        char *sslkey_path;
        char *sslkey_pwd;
        char *ca_path;
    };

    CurlKit() {

        curl_ = curl_easy_init();
        if (curl_) {
            curl_easy_setopt(curl_, CURLOPT_FOLLOWLOCATION, 1L);
            curl_easy_setopt(curl_, CURLOPT_NOSIGNAL, 1L);
            curl_easy_setopt(curl_, CURLOPT_TIMEOUT, 30);
        }
    };

    ~CurlKit() {
        if (curl_) {
            curl_easy_cleanup(curl_);
        }
    };

    CURL *getHandler() {
        return this->curl_;
    }

    //cookie
    CurlKit &cookie(const string cookie) {
        curl_easy_setopt(curl_, CURLOPT_COOKIE, cookie.c_str());
        return *this;
    };

    //debug
    CurlKit &debug() {
        if (curl_) {
            curl_easy_setopt(curl_, CURLOPT_VERBOSE, 1L);
        }
        return *this;
    };

    //proxy
    CurlKit &proxy(const string &proxy) {
        if (curl_) {
            curl_easy_setopt(curl_, CURLOPT_PROXY, proxy.c_str());
        }
        return *this;
    };

    //ssl
    CurlKit &ssl(struct SslPem *sslInfo) {
        return ssl(false, sslInfo);
    };

    //Two-factor authentication
    CurlKit &ssl(bool tfa, struct SslPem *sslInfo) {
        if (curl_) {
            if (sslInfo->sslcert_path) {
                curl_easy_setopt(curl_, CURLOPT_SSLCERTTYPE, "PEM");
                curl_easy_setopt(curl_, CURLOPT_SSLCERT, sslInfo->sslcert_path);
                if (sslInfo->sslcert_pwd)
                    curl_easy_setopt(curl_, CURLOPT_SSLCERTPASSWD, sslInfo->sslcert_pwd);
            }

            if (sslInfo->sslkey_path) {
                curl_easy_setopt(curl_, CURLOPT_SSLKEYTYPE, "PEM");
                curl_easy_setopt(curl_, CURLOPT_SSLKEY, sslInfo->sslkey_path);
                if (sslInfo->sslcert_pwd)
                    curl_easy_setopt(curl_, CURLOPT_KEYPASSWD, sslInfo->sslkey_pwd);
            }

            if (sslInfo->ca_path)
                curl_easy_setopt(curl_, CURLOPT_CAINFO, sslInfo->ca_path);

            curl_easy_setopt(curl_, CURLOPT_SSL_VERIFYPEER, tfa);
            curl_easy_setopt(curl_, CURLOPT_SSL_VERIFYHOST, tfa);
        }
        return *this;
    };

    //header
    CurlKit &header(const string &header) {
        headerList_ = curl_slist_append(headerList_, header.c_str());
        return *this;
    };

    CurlKit &headers(map<string, string> *headers) {
        for (auto item : *headers) {
            string header = item.first + ": " + item.second;
            curl_slist_append(headerList_, header.c_str());
        }
        return *this;
    };

    //method
    CurlKit &get(const string &url) {
        if (curl_) {
            curl_easy_setopt(curl_, CURLOPT_HTTPGET, 1L);
            curl_easy_setopt(curl_, CURLOPT_URL, url.c_str());
        }
        return *this;
    };

    CurlKit &post(const string &url) {
        if (curl_) {
            curl_easy_setopt(curl_, CURLOPT_HTTPPOST, 1L);
            curl_easy_setopt(curl_, CURLOPT_URL, url.c_str());
        }
        return *this;
    };

    CurlKit &put(const string &url) {
        if (curl_) {
            curl_easy_setopt(curl_, CURLOPT_PUT, 1L);
            curl_easy_setopt(curl_, CURLOPT_URL, url.c_str());
        }
        return *this;
    };

    CurlKit &del(const string &url) {
        if (curl_) {
            curl_easy_setopt(curl_, CURLOPT_CUSTOMREQUEST, "DELETE");
            curl_easy_setopt(curl_, CURLOPT_URL, url.c_str());
        }
        return *this;
    };

    //request
    string request() {
        CurlKit::MemData memData;
        memData.data = (char *) malloc(1);
        memData.size = 0;

        if (curl_) {
            if (headerList_ != nullptr) {
                curl_easy_setopt(curl_, CURLOPT_HTTPHEADER, headerList_);
            }

            curl_easy_setopt(curl_, CURLOPT_WRITEFUNCTION, WriteMemoryCallback);
            curl_easy_setopt(curl_, CURLOPT_WRITEDATA, &memData);

            res_ = curl_easy_perform(curl_);
        }

        string result = memData.data;
        free(memData.data);

        return result;
    };

    string request(const string &rawData) {
        return request(rawData, "");
    };

    string request(const string &rawData, const string &contentType) {
        CurlKit::MemData memData;
        memData.data = (char *) malloc(1);
        memData.size = 0;

        char *rawDataStr = const_cast<char *>(rawData.c_str());

        if (curl_) {
            if (!contentType.empty()) {
                string contentTypeHeader = "Content-Type: " + contentType;
                header(contentTypeHeader);
            }

            if (headerList_ != nullptr) {
                curl_easy_setopt(curl_, CURLOPT_HTTPHEADER, headerList_);
            }

            curl_easy_setopt(curl_, CURLOPT_UPLOAD, 1L);
            curl_easy_setopt(curl_, CURLOPT_READFUNCTION, ReadMemoryCallback);
            curl_easy_setopt(curl_, CURLOPT_READDATA, rawDataStr);
            curl_easy_setopt(curl_, CURLOPT_INFILESIZE_LARGE, (curl_off_t) (strlen(rawDataStr)));

            curl_easy_setopt(curl_, CURLOPT_WRITEFUNCTION, WriteMemoryCallback);
            curl_easy_setopt(curl_, CURLOPT_WRITEDATA, &memData);

            res_ = curl_easy_perform(curl_);
        }

        std::string result = memData.data;
        free(memData.data);

        return result;
    };

    bool upload(const string &filePath) {
        struct stat fileInfo;
        stat(filePath.c_str(), &fileInfo);

        FILE *file = nullptr;
        fopen_s(&file, filePath.c_str(), "rb");

        if (curl_) {
            if (headerList_ != nullptr) {
                curl_easy_setopt(curl_, CURLOPT_HTTPHEADER, headerList_);
            }

            curl_easy_setopt(curl_, CURLOPT_UPLOAD, 1L);
            curl_easy_setopt(curl_, CURLOPT_READFUNCTION, ReadFileCallback);
            curl_easy_setopt(curl_, CURLOPT_READDATA, file);
            curl_easy_setopt(curl_, CURLOPT_FOLLOWLOCATION, 0L);
            curl_easy_setopt(curl_, CURLOPT_INFILESIZE_LARGE, (curl_off_t) fileInfo.st_size);

            res_ = curl_easy_perform(curl_);
        }

        fclose(file);

        return res_ == CURLE_OK;
    };

    bool download(const string &filePath) {
        FILE *file = nullptr;
        fopen_s(&file, filePath.c_str(), "wb");
        if (curl_) {
            if (headerList_ != nullptr) {
                curl_easy_setopt(curl_, CURLOPT_HTTPHEADER, headerList_);
            }

            curl_easy_setopt(curl_, CURLOPT_WRITEFUNCTION, WriteFileCallback);
            curl_easy_setopt(curl_, CURLOPT_WRITEDATA, file);

            res_ = curl_easy_perform(curl_);
        }

        fclose(file);
        return res_ == CURLE_OK;
    };

    void preDownload(const string &filePath) {
        if (curl_) {
            if (headerList_ != nullptr) {
                curl_easy_setopt(curl_, CURLOPT_HTTPHEADER, headerList_);
            }

            curl_easy_setopt(curl_, CURLOPT_WRITEFUNCTION, multiWriteFileCallback);
            curl_easy_setopt(curl_, CURLOPT_WRITEDATA, filePath.c_str());
        }
    };

    //response header: response code ; cookielist ; content length ; CURLINFO_CONTENT_TYPE
    long responseCode() {
        long retcode = 0;
        if (curl_) {
            curl_easy_getinfo(curl_, CURLINFO_RESPONSE_CODE, &retcode);
        }
        return retcode;
    };

    list<string> *responseCookielist() {
        list<string> list;
        struct curl_slist *slist;
        if (curl_) {
            curl_easy_getinfo(curl_, CURLINFO_COOKIELIST, &slist);
        }
        while (slist->data != nullptr) {
            list.push_back(slist->data);
            if (slist->next == nullptr) {
                break;
            }
            slist = slist->next;
        }
        return &list;
    };

    string responseContentType() {
        char *type;
        if (curl_) {
            curl_easy_getinfo(curl_, CURLINFO_CONTENT_TYPE, &type);
        }
        return type;
    };

    double responseContentLength() {
        double length = 0;
        if (curl_) {
            curl_easy_getinfo(curl_, CURLINFO_CONTENT_LENGTH_DOWNLOAD, &length);
        }
        return length;
    };
};

#endif
