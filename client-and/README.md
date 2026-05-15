
# Adria web project group-22
This is the client-side start project for the Analysis and Development project.

Create your client-side **VUE** project in this repo.

## Important public urls  
* Web project: https://project-2.ti.howest.be/2024-2025/group-22/
* Sonar reports: https://sonarqube.ti.howest.be/dashboard?id=2024.ad-project.adria.22.client

## Please complete the following instructions before committing the **final version** on the project
Please **add** any **instructions** required to: 

* make your application work if applicable 
* be able to test the application (credentials, populated db, ...)

Also clarify
* If there are known **bugs**.
* If you haven't managed to finish certain required functionality.
  
## Instructions for local quality checks
You can run the validators for html, CSS and JS rules locally. 

Make sure **npm** is installed.

There is no need to push to the server to check if you are compliant with our rules. 

In the interest of sparing the server, please result to local testing as often as possible. 

If everyone pushes to test, the remote will not last. 

Open a terminal in your IDE
  - Make sure you are in the root folder of the client project.
  - Execute `npm install` this step is only needed once.
  - Execute `npm run validate-on-macos` for linux/mac users.
  - Execute `npm run validate-on-windows` for Windows users. 

**Code coverage isn't needed for the client side, take it as a challenge"**

**Hint:**

If you want to skip ci remotely, include `[ci skip]` in your commit message. 

This is convenient for when you want to quickly add a certain commit, but do not wish to trigger the whole CI sequence. 

## cleaninstall

This template should help get you started developing with Vue 3 in Vite.

## Customize configuration

See [Vite Configuration Reference](https://vitejs.dev/config/).

## Project Setup

```sh
npm install
```

## Compile and Hot-Reload for Development

```sh
//Run the adria-server with gradle run (through your IDE)
npm run dev
```

## Compile and Minify for Production

```sh
npm run build
```