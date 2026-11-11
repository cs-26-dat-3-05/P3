import { Card, StepHeader, HeaderLabel, HeaderTitle, StepRow, StepCircle, StepText, StepTitle, StepDescription, StepDate} from "./style"
export interface Step {
    number: number;
    title: string;
    description?: string;
    date?: string;
    status: "done" | "current" | "upcoming";
}

const MOCK_STEPS: Step[] = [
    {
        number: 1,
        title: "Returnering registreret",
        description: "Registreret automatisk fra Danske Banks kontoudtog",
        date: "18.06.2025",
        status: "done",
    },
    {
        number: 2,
        title: "Arrangør identificeret",
        description: "Kontaktoplysninger kontrolleret, herunder e-mail",
        date: "18.06.2025",
        status: "done",
    },
    {
        number: 3,
        title: "Nye bankoplysninger efterspurgt",
        description: "Automatisk mail sendt",
        date: "18.06.2025",
        status: "done",
    },
    {
        number: 4,
        title: "Afventer korrekt kontonummer",
        status: "current",
    },
    {
        number: 5,
        title: "Nye oplysninger indtastet",
        status: "upcoming",
    },
    {
        number: 6,
        title: "Udbetaling genindsendt",
        status: "upcoming",
    }
];

export default function AktuelleTrin() {
    const currentStep = MOCK_STEPS.find((step) => step.status === "current");
    const totalSteps = MOCK_STEPS.length;
    return (
        <Card>
            <StepHeader>
                <div>
                    <HeaderLabel>AKTUELLE TRIN</HeaderLabel>
                    <HeaderTitle>{currentStep?.title}</HeaderTitle>
                </div>
                <span>TRIN {currentStep?.number} / {totalSteps}</span>
            </StepHeader>
            
            {MOCK_STEPS.map((step) => (
                <StepRow key={step.number}>
                    <StepCircle $status={step.status}>{step.status === "done" ? "✓" : step.number}</StepCircle>
                    <StepText>
                        <StepTitle $status={step.status}>{step.title}</StepTitle>
                        <StepDescription>{step.description}</StepDescription>
                    </StepText>
                    <StepDate>{step.date}</StepDate>
                </StepRow>
            ))}
        </Card>
    );
}