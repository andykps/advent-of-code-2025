#!/usr/bin/env bash

if [ "$#" -ne 1 ]; then
    echo "Usage: $0 <input_file>"
    exit 1
fi

input_file="$1"
declare -a fresh_db
fresh_count=0

while read -r line; do
    if [[ "$import_done" = true ]]; then
        for entry in "${fresh_db[@]}"; do
            start=${entry%-*}
            end=${entry#*-}
#            echo "Checking line: $line against range: ${start}-${end}"
            if [[ $line -ge ${start} && $line -le ${end} ]]; then
               ((fresh_count++))
               break
            fi
        done
    elif [[ -z "$line" ]]; then
        import_done=true
#        echo ${fresh_db[@]}
    else
        fresh_db+=($line)
    fi
done < "$input_file"

echo "$fresh_count"
