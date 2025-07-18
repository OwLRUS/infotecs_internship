#!/bin/bash

dos2unix prepairing_tests.sh
chmod +x prepairing_tests.sh
./prepairing_tests.sh

if ! ./test_add_pair.exp; then
    echo "Add pair - ERROR!"
    exit 1
fi

if ! ./test_add_pair_negative.exp; then
    echo "Add pair negative - ERROR!"
    exit 1
fi

if ! ./test_print_list.exp; then
    echo "Print list - ERROR!"
    exit 1
fi

if ! ./test_print_list_exist.exp; then
    echo "Print list with exist pair - ERROR!"
    exit 1
fi

if ! ./test_get_IP.exp; then
    echo "Get IP - ERROR!"
    exit 1
fi

if ! ./test_get_IP_negative.exp; then
    echo "Get IP negative - ERROR!"
    exit 1
fi

if ! ./test_get_domain.exp; then
    echo "Get domain - ERROR!"
    exit 1
fi

if ! ./test_get_domain_negative.exp; then
    echo "Get domain negative - ERROR!"
    exit 1
fi

if ! ./test_config_editor.exp; then
    echo "Config editor - ERROR!"
    exit 1
fi

if ! ./test_config_editor_negative.exp; then
    echo "Config editor negative - ERROR!"
    exit 1
fi

if ! ./test_delete_pair_IP.exp; then
    echo "Delete pair by IP - ERROR!"
    exit 1
fi

./test_add_pair.exp
if ! ./test_delete_pair_domain.exp; then
    echo "Delete pair by domain - ERROR!"
    exit 1
fi

if ! ./test_delete_pair_negative.exp; then
    echo "Delete pair negative - ERROR!"
    exit 1
fi

echo "All tests are passed!"

