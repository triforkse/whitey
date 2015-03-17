# Whitey

A IntelliJ IDEA plugin that adds some nifty utilities for working with whitespace in source code. ## Developer's Guide

Run:

```bash
make setup
```

This will download IntelliJ IDEA Community Edition into `./idea` which is a dependency
for building — and is rather large.

The project should be built from within IntelliJ IDEA itself, which makes sense.


## Actions

All actions support multiple cursors.

__Remove All Adjacent__ Remove all whitespace before and after the caret(s).