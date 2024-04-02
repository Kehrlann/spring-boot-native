#!/usr/bin/env bash

gcloud compute scp --zone "europe-west9-a" --project "cf-identity-service-oak" simple/build/native/nativeCompile/simple daniel-native-vm:~/native-app-mac
