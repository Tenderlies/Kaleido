#ifndef CURLMULTIKIT_H_
#define CURLMULTIKIT_H_

#include "curlkit.h"

class CurlMultiKit
{
  private:
    CURLM *curlm_;

  public:
    CurlMultiKit()
    {
        curlm_ = curl_multi_init();
    };

    ~CurlMultiKit()
    {
        curl_multi_cleanup(curlm_);
    };

    void multiProcess(list<CurlKit *> handleList)
    {
        for (auto item : handleList)
        {
            curl_multi_add_handle(curlm_, item->getHandler());
        }

        int running = 0;
        do
        {
            int numfds = 0;
            curl_multi_wait(curlm_, NULL, 0, 10 * 1000, &numfds);
            curl_multi_perform(curlm_, &running);
        } while (running);

        for (auto item : handleList)
        {
            curl_multi_remove_handle(curlm_, item->getHandler());
        }
    };
};

#endif
