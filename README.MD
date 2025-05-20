# ChangeDetector

ChangeDetector is a program that detects modifications, deletions, or creations of files and folders within the current directory. It then sends details about these changes to various sources.

## Supported Sources

- [x] Server-Sent Events (SSE) - Ideal for browser access (e.g., a script in a website's development branch can automatically connect to the SSE stream for live reloading).
- [x] Webhooks/HTTP Posts (Discord, Slack, etc)

## License

This project is licensed under the GPLv3 License. See the [LICENSE](#) file for details.
