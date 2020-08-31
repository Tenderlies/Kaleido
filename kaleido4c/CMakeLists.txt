cmake_minimum_required(VERSION 3.11.4)
project(cstrom VERSION 0.1.0)

FetchContent_Declare(
  Boost
  GIT_REPOSITORY https://github.com/boostorg/boost.git
  GIT_TAG        boost-1.70.0
)

###############
# Build     ###
###############
if(MSVC)
  include_directories($ENV{EXTERNAL_LIB}/include)
  link_directories($ENV{EXTERNAL_LIB}/lib)
  add_definitions(/DCURL_STATICLIB)
  set(CMAKE_EXE_LINKER_FLAGS /NODEFAULTLIB:\"libcmt.lib\")
endif()

set(cstrom_sources
        src/main.cc
        src/getopt.h
        src/getopt.c
        )

add_executable(cstrom ${cstrom_sources})

if(MSVC)
  target_link_libraries(cstrom libcurl.lib ws2_32.lib winmm.lib wldap32.lib crypt32.lib normaliz.lib)
else()
  target_link_libraries(cstrom curl pthread)
endif()

###############
# Test      ###
###############

enable_testing()

add_definitions(/DCSTORM_TEST_URL=http:\\/\\/ip:port)

if(MSVC)
  set(variables 
        CMAKE_CXX_FLAGS_DEBUG
        CMAKE_CXX_FLAGS_RELEASE
        CMAKE_CXX_FLAGS_RELWITHDEBINFO
        CMAKE_CXX_FLAGS_MINSIZEREL
  )
  foreach(variable ${variables})
      if(${variable} MATCHES "/MD")
        string(REGEX REPLACE "/MD" "/MT" ${variable} "${${variable}}")
      endif()
  endforeach()
  set(CMAKE_CXX_FLAGS "/w")
endif()

add_subdirectory(3rd/googletest/googlemock)

include_directories(${gtest_SOURCE_DIR}/include ${gmock_SOURCE_DIR}/include)

set($ENV{CSTORM_TEST_URL} http://ip:port)

set(test_sources 
        test/test_test.cc
        )

add_executable(unitTest ${test_sources})

if(MSVC)
  target_link_libraries(unitTest libcmt.lib)
endif()

target_link_libraries(unitTest gmock_main)

add_test(cstrom_test unitTest)

###############
# Pack      ###
###############

set(CPACK_PROJECT_NAME ${PROJECT_NAME})
set(CPACK_PROJECT_VERSION ${PROJECT_VERSION})

include(CPack)

