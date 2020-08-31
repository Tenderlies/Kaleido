#ifndef ARGS_H_
#define ARGS_H_

#include <stdlib.h>
#include <string>
#include "iostream"
#include "getopt.h"

using std::cout;
using std::endl;

class Args {
private:
    Args(){};

    Args(Args const &);

    Args &operator=(Args const &);

    ~Args(){};

public:
    static Args &instance() {
        static Args singleArgs;
        return singleArgs;
    };

    int readArgs(int *argc, char ***argv) {
        const option longOptions[] = {
                {"help", no_argument,       NULL, 'h'},
                {"echo", required_argument, NULL, 'e'},
                {NULL, 0,                   NULL, 0}};

        int ch;

        while ((ch = getopt_long(*argc, *argv, "e:h", longOptions, NULL)) != EOF) {
            switch (ch) {
                case 'e':
                    cout << optarg << endl;
                    continue;
                case 'h':
                    cout << "HELP" << endl;
                default:
                    return 0;
            }
        }

        return -1;
    };

};

#endif