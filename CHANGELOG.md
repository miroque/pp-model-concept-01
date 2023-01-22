# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

[//]: <> (✨ Added, 🛠 Fixed, ♻ Changed, 🔥 Removed)


## [unreleased] 

### ✨ Added

- added UUID type, with autogenerate-value if *id/nid* is null

### ♻ Changed

- in most cases *id* ➡️ *nid*
- *id/nid* - **String** ➡️ **UUID**

## v [0.4.0] - 2023-01-04

### ✨ Added

- Find method for searching Knowledge with string value
- Find method for searching Knowledge with nid ("natural"-id)

## v [0.3.0] - 2023-01-02

### ✨ Added

- Docker files for better experiance
- Docker-compose file for better experiance

### ♻ Changed

- Bumped java version from 11 to 17
- Bumped quarkus from 2.9.2.Final to 2.14.2.Final

## v [0.2.0] - 2022-12-10

### ✨ Added

- "Show" all Knowledges in Storage
- "Show" all Knowledges at "root" level
- "Show" all Knowledges in chosen Knowledge (like branch select)

### ♻ Changed

- XSD Scheme add constrains
- ROADMAP has changed, rearange docker-image step

## v [0.1.0] - 2022-12-03

### 🛠 Fixed

- Pretty print XML formatter

### 🔥 Removed

- XSD Validation

[unreleased]: https://github.com/miroque/pp-model-concept-01/compare/0.4.0..HEAD

[0.4.0]: https://github.com/miroque/pp-model-concept-01/releases/tag/0.4.0
[0.3.0]: https://github.com/miroque/pp-model-concept-01/releases/tag/0.3.0
[0.2.0]: https://github.com/miroque/pp-model-concept-01/releases/tag/0.2.0
[0.1.0]: https://github.com/miroque/pp-model-concept-01/releases/tag/0.1.0
