#ifndef UTILS_H_
#define UTILS_H_

#ifdef __unix
#define fopen_s(pFile, filename, mode) ((*(pFile))=fopen((filename),(mode)))==NULL

#include <sys/stat.h>

#endif

class Utils {


};

#endif