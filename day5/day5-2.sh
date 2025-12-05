#!/usr/bin/env bash

if [ "$#" -ne 1 ]; then
    echo "Usage: $0 <input_file>"
    exit 1
fi

input_file="$1"
declare -a fresh_db
fresh_count=0

while read -r line; do
    if [[ -z "$line" ]]; then
        break
    else
        len=${#fresh_db[@]}
        for ((i=0; i<${#fresh_db[@]}; i++)); do
            if (( ${line%-*} > ${fresh_db[$i]%-*} )); then
                continue
            elif (( ${line%-*} < ${fresh_db[$i]%-*} )); then
                fresh_db=("${fresh_db[@]:0:i}" "$line" "${fresh_db[@]:i}")
                break
            else
                if (( ${line#*-} > ${fresh_db[$i]#*-} )); then
                    continue
                else
                    fresh_db=("${fresh_db[@]:0:i}" "$line" "${fresh_db[@]:i}")
                    break
                fi
            fi
        done
        if (( ${#fresh_db[@]} == len )); then
            fresh_db+=("$line")
        fi
    fi
done < "$input_file"

prev_end=0
for line in ${fresh_db[@]}; do
    start=${line%-*}
    end=${line#*-}
    if (( prev_end >= start )); then
        start=$((prev_end + 1))
    fi
    if (( end >= start )); then
        fresh_count=$((fresh_count + end - start + 1))
    fi
    if (( end > prev_end )); then
        prev_end=$end
    fi
#    echo "Processing range: ${line}, counted fresh: $fresh_count, prev_end: $prev_end, start: $start, end: $end"
done

echo "${fresh_count}"
