# TagHelper
[![LICENSE](https://img.shields.io/github/license/Samarium150/TagHelper)](https://github.com/Samarium150/TagHelper/blob/main/LICENSE)
[![Gradle CI](https://github.com/Samarium150/TagHelper/actions/workflows/Gradle%20CI.yml/badge.svg)](https://github.com/Samarium150/TagHelper/actions/workflows/Gradle%20CI.yml)

A Minecraft mod for editing **Named Binary Tags** (NBT) by commands.

## Usage

- `/tag-helper get`  
  Get NBT of the item on the main hand.
- `/tag-helper set <key> <value>`  
  Add or Replace`{tag: value}` in NBT of the item on the main hand.
- `/tag-helper set <NBT>`  
  Set NBT of the item on the main hand as `<NBT>`
- `/tag-helper remove <key>`  
  Remove `{tag: value}` in NBT of the item on the main hand.
- `/tag-helper remove`  
  Remove NBT of the item on the main hand.

`/th` is an alias of `/tag-helper`
