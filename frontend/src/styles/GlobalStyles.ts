import { createGlobalStyle } from "styled-components";

/*
 * HOW TO USE
 *
 * Farver / variabler:
 *   color: var(--primary-color);
 *   border: var(--border);
 *
 * Global klasse i TSX:
 *   <div className="box">...</div>
 *
 * Global klasse på styled-component:
 *   const Card = styled.div.attrs({ className: "box" })`...`;
 *
 *
 * Klasser: box
 */

const GlobalStyles = createGlobalStyle`
  :root {
    --primary-color: #333;
    --background-color: #ffffff;
    --font-main: 'Helvetica Neue', sans-serif;

    /* Border style */
    --ink-shadow: #8c8c8c;
    --border: 2px solid var(--primary-color);
    --border-sm: 1.5px solid var(--primary-color);
    --shadow: 4px 4px 0 var(--ink-shadow);
    --shadow-sm: 2px 2px 0 var(--ink-shadow);
  }

  body {
    margin: 0;
    padding: 0;
    background-color: var(--background-color);
    font-family: var(--font-main);
  }
  .box {
    background-color: var(--paper);
    border: var(--border);
    box-shadow: var(--shadow);
    padding: 8px;
  }
`;
export default GlobalStyles;
