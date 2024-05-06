#!/usr/bin/env bash

gcloud compute scp \
  --zone "europe-west9-a" \
  --project "cf-identity-service-oak" \
  books/build/native/nativeCompile/books \
  daniel-native-vm:~/native-app-mac
