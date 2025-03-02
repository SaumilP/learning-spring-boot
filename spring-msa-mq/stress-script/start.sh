#!/bin/sh

vegeta attack -targets=targets.txt -duration=600s -keepalive -name 'API Stress Test' -max-connections=700 -max-workers=200 -rate=250/5ms -workers=100 -timeout=1s | tee results.bin | vegeta report