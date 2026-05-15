import { fileURLToPath, URL } from 'node:url';
import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import { VitePWA } from 'vite-plugin-pwa';

// https://vitejs.dev/config/
function resolveAliases() {
  return {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
      '@vue-leaflet/vue-leaflet': fileURLToPath(
          new URL('node_modules/@vue-leaflet/vue-leaflet/dist/vue-leaflet', import.meta.url)
      ),
      leaflet: fileURLToPath(new URL('node_modules/leaflet/dist/leaflet.js', import.meta.url)),
    },
  };
}

function configurePlugins(mode) {
  return [
    vue(),
    VitePWA({
      srcDir: "src",
      filename: "service-worker.js",
      strategies: "injectManifest",
      injectRegister: false,
      manifest: false,
      injectManifest: {
        injectionPoint: null,
      },
      devOptions: {
        enabled: mode !== "production",
      },
    }),
  ];
}

function configureBuild() {
  return {
    rollupOptions: {
      external: [], // Add libraries here if needed
    },
  };
}

function configureEsbuild() {
  return {
    supported: {
      "top-level-await": true,
    },
  };
}

export default defineConfig(({ mode }) => ({
  base: mode === "production" ? "/2024-2025/group-22/" : "/",
  plugins: configurePlugins(mode),
  resolve: resolveAliases(),
  build: configureBuild(),
  esbuild: configureEsbuild(),
}));