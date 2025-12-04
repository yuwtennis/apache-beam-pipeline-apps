#!/usr/bin/env bash

WORK_DIR="./work-tmp"
FLINK_SHA512_URL_BASE="https://downloads.apache.org/flink"
FLINK_DOWNLOAD_URL_BASE="https://dlcdn.apache.org/flink"

function cleanup() {
  rm -rf $WORK_DIR
}

function download_flink_and_extract() {
  local flink_version=$1
  local scala_version=$2
  local current_dir=$3
  local flink_file="flink-${flink_version}-bin-scala_${scala_version}.tgz"
  local download_url="${FLINK_DOWNLOAD_URL_BASE}/flink-${flink_version}/${flink_file}"
  local sha512_url="${FLINK_SHA512_URL_BASE}/flink-${flink_version}/${flink_file}.sha512"

  curl -XGET -s -O -L "${download_url}"

  # TODO This should be outside this function for avoiding deep nesting
  # Integrity check
  curl -XGET -s "${sha512_url}" >> "${flink_file}.sha512"
  sha512sum "$flink_file" >> "${flink_file}.downloaded.sha512"
  diff "${flink_file}.sha512" "${flink_file}.downloaded.sha512" || exit 1
  echo "$flink_file is a valid file."

  mkdir "flink-${flink_version}" &&  \
  tar xf "$flink_file" -C "flink-${flink_version}" --strip-components=1 && \
  mv "flink-${flink_version}" "$current_dir"

  echo "$flink_file"
}

function main() {
  local app_type=""
  local app_ver=""
  local scala_ver=""
  echo "Script start!"

  local current_dir=""
  mkdir -p $WORK_DIR && cd $WORK_DIR || exit 1

  while getopts "t:a:s:" opt; do
    case $opt in
      t)
        app_type=$OPTARG
        echo "Application type: $app_type"
        ;;
      a)
        app_ver=$OPTARG
        echo "Application version: $app_ver"
        ;;
      s)
        scala_ver=$OPTARG
        echo "Scala version: $scala_ver"
        ;;
      ?)
        echo "Unknown option specified"
        exit 1
        ;;
      *)
        echo "Invalid options"
        exit 1
        ;;
    esac
  done

  current_dir=$(pwd)

  case $app_type in
    flink)
      download_flink_and_extract "$app_ver" "$scala_ver" "$current_dir"
        ;;
    *)
      echo "Unkown application type."
      exit 1
      ;;
  esac

  cleanup
}

main "$@"