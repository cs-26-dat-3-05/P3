import styled from "styled-components";

export const Container = styled.section.attrs({ className: "box" })`
  && {
    width: 100%;
    padding: 0;
    background: #f9f8f4;
    font-family: "Courier New", monospace;
  }
`;

export const OverviewHeader = styled.header`
  height: 64px;
  box-sizing: border-box;
  padding: 0 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;

  h2 {
    margin: 0;
    font:
      700 15px Arial,
      sans-serif;
  }
  span {
    font-size: 15px;
  }
`;

export const Filters = styled.div`
  display: grid;
  grid-template-columns: 1.2fr 0.86fr 0.86fr 0.86fr;
  gap: 14px;
  padding: 9px 20px 17px;
  border-bottom: 1px solid #888;

  label {
    display: grid;
    gap: 6px;
    color: #666;
    font-size: 10px;
  }
  input,
  select {
    width: 100%;
    height: 54px;
    box-sizing: border-box;
    border: 3px solid #5b5b5b;
    border-radius: 0;
    padding: 0 14px;
    color: #333;
    background: #fbfaf6;
    font:
      14px "Courier New",
      monospace;
  }
  @media (max-width: 760px) {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
  @media (max-width: 460px) {
    grid-template-columns: 1fr;
  }
`;

export const TableWrap = styled.div`
  max-height: 520px;
  overflow: auto;
`;

export const CaseTable = styled.table`
  width: 100%;
  min-width: 1150px;
  border-collapse: collapse;
  table-layout: fixed;
  th,
  td {
    text-align: left;
    border-bottom: 1px solid #aaa;
  }
  th {
    position: sticky;
    top: 0;
    z-index: 1;
    height: 46px;
    padding: 0 20px;
    background: #e7e5df;
    font-size: 11px;
    font-weight: 400;
  }
  td {
    height: 84px;
    padding: 0 20px;
    font-size: 13px;
  }
  th:nth-child(1) {
    width: 20%;
  }
  th:nth-child(2) {
    width: 10%;
  }
  th:nth-child(3) {
    width: 16%;
  }
  th:nth-child(4) {
    width: 15%;
  }
  th:nth-child(5) {
    width: 9%;
  }
  th:nth-child(6) {
    width: 18%;
  }
  th:nth-child(7) {
    width: 9%;
  }
`;

export const CaseName = styled.div`
  font-weight: 700;
  span {
    display: block;
    margin-top: 5px;
    color: #777;
    font-weight: 400;
  }
`;

export const Status = styled.span`
  display: inline-block;
  max-width: 100%;
  padding: 5px 10px;
  border: 2px solid #777;
  border-radius: 18px;
  white-space: nowrap;
  font-size: 11px;
`;

export const Progress = styled.div`
  color: #777;
  font-size: 12px;
  white-space: nowrap;
  div {
    height: 10px;
    margin-top: 7px;
    border: 2px solid #777;
    background: #f9f8f4;
  }
  span {
    display: block;
    height: 100%;
    background: #777;
  }
`;

export const CaseRow = styled.tr<{ $attention: boolean }>`
  cursor: pointer;
  background: ${({ $attention }) =>
    $attention ? "var(--background-gradient)" : "var(--background-color)"};
`;
export const FilterSelect = styled.select`
  cursor: pointer;
`;
