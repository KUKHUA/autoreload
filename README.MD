# ChangeDetector

ChangeDetector is a program that detects modifications, deletions, or creations of files and folders within the current directory. It then sends details about these changes to various sources (SSE, webhooks)

## Usage
To setup ChangeDetector, run: `changedetector setup`. You'll be guided through a few prompts to configure the program.

Some configuration is not avaiable in setup command yet:
* blacklisted.files - Comma-seperated list of files names to exclude.
 
    *Example:*  `"blacklisted.files": "Config.java,FileFilter.java`
* blacklisted.folders - Comma-separated list of folder paths to exclude from monitoring. Please not that where you run this from will have a `.`. For example, instead of `"/home/u/Git/Projects/autoreload/src/http,"`, you will have to do `"/home/u/Git/Projects/autoreload/./src/http,"`, if you are running it from the autoreload folder.

    *Example:* `"blacklisted.folders": "/home/lucas/Gits/Java Projects/autoreload/./src/http,null"`


Once setup is complete, start monitoring the current folder with:
After that, run `changedetector start` to actually start watching the current folder.

### Example: Auto-Reloading a Development Webpage

One practical use case is auto-reloading a web page during development. Simply add the following JavaScript snippet to your page:
```javascript
const eventSource = new EventSource("http://127.0.0.1:1993");
eventSource.onmessage = function (event) {
    window.location.reload();
};

```
> **Note:** Make sure to change the port.

>  **Note:** Due to CORS restrictions, make sure to access your development site via `http://127.0.0.1:8080` instead of `http://localhost:8080`, for example.


## Installation
### Pre-Built
1. Download the latest `changedetector.jar` release [here](https://github.com/KUKHUA/changedetecter/releases).
3. Make sure you have Java installed. You can adopt this Java, if you want. https://adoptium.net/
2. Add this to your path.
    * On Windows:
        1.  Place `changedetector.jar` in a permanent location, such as `C:\changedetector.jar`.

        2.  Open the Start menu and search for **Environment Variables**.

        3.  Click **Edit the system environment variables**.

        4.  In the **System Properties** window, click **Environment Variables**.

        5.  Under **System Variables**, find the **Path** variable and select Edit.

        6. Add a path for "changedetector" to "java -jar C:\changedetector.jar" or something, idk.

        7.  Click **OK** on all winbdows and restart your terminal or PC.
    * On Linux:
        1.  Move `jsb.jar` to a desired directory, (e.g.,`/usr/local/lib/jsb.jar`).

        2.  Open a terminal and edit your shell configuration file:
            * For **Bash**: `nano ~/.bashrc`
            * For **Zsh** : `nano ~/.zshrc`

        3. Add a alias for `changedetector`
            ```bash
            alias jsb="java -jar /usr/local/lib/changedetector.jar"
            ```

        4. Relaunch your terminal, or reload your `rc` file:
            *  **Bash**: `source ~/.bashrc `
            *  **Zsh** (MacOS): `source ~/.zshrc` 
3. Your done.

### Building yourself
```bash
    git clone https://github.com/KUKHUA/changedetecter.git
    cd changedetecter
    find ./src -name "*.java" > sources.txt
    javac -d ./classes @sources.txt  # Use the list of found files for compilation
    jar cfe ./dist/changedetector.jar Main -C ./classes .
    echo "its built to ./dist/changedetector.jar"
````

## License

This project is licensed under the GPLv3 License. See the [LICENSE](#) file for details.
