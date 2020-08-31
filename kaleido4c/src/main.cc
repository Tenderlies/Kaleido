#include <fstream>

#include "curlkit.h"
#include "args.h"

using std::cout;
using std::endl;
using std::string;

int main(int argc, char **argv) {

    CurlKit kit;

    kit.get("https://tenderlies.github.io").request();

    cout << kit.responseCode() << endl;

    cout << "Hello World!" << endl;

    Args::instance().readArgs(&argc, &argv);

    return 0;
}
