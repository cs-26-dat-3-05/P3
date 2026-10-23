import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'


const apiTarget = process.env.VITE_API_PROXY_TARGET ?? 'http://localhost:8080'

export default defineConfig({
  plugins: [react()],
  server: {
    host: true,
    port: 5173,
    // File watching through a Docker volume does not always pick up changes
    // (especially on Windows), so polling can be switched on with an env var.
    watch: process.env.VITE_USE_POLLING === 'true' ? { usePolling: true } : undefined,
    proxy: {
      '/api': { target: apiTarget, changeOrigin: true },
    },
  },
})
