#include <gmock/gmock.h>
#include <gtest/gtest.h>

TEST(MethodTest, eqTest)
{
    ASSERT_EQ("0", "0");
}

class DemoTest : public ::testing::Test
{
};
