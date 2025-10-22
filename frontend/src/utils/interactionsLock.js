// Minimal, idempotent global interaction lock

let locked = false;

const keyBlocker = (e) => {
    e.preventDefault();
    e.stopPropagation();
    return false;
};

export function lockUI() {
    if (locked) return;
    locked = true;

    // Blur current focus so Enter/Space etc. won’t act
    if (document.activeElement && document.activeElement instanceof HTMLElement) {
        document.activeElement.blur();
    }

    // Make app subtree non-interactive (focus & pointer)
    const appRoot = document.getElementById("app");
    if (appRoot) appRoot.setAttribute("inert", "");

    // Block all keyboard events (capture phase)
    document.addEventListener("keydown", keyBlocker, true);
    document.addEventListener("keypress", keyBlocker, true);
    document.addEventListener("keyup", keyBlocker, true);

    // Remember and override body interaction
    const b = document.body;
    b.dataset._prevOverflow = b.style.overflow || "";
    b.dataset._prevPointer = b.style.pointerEvents || "";
    b.style.overflow = "hidden";
    b.style.pointerEvents = "none";
}

export function unlockUI() {
    if (!locked) return;
    locked = false;

    const appRoot = document.getElementById("app");
    if (appRoot) appRoot.removeAttribute("inert");

    document.removeEventListener("keydown", keyBlocker, true);
    document.removeEventListener("keypress", keyBlocker, true);
    document.removeEventListener("keyup", keyBlocker, true);

    const b = document.body;
    b.style.overflow = b.dataset._prevOverflow || "";
    b.style.pointerEvents = b.dataset._prevPointer || "";
    delete b.dataset._prevOverflow;
    delete b.dataset._prevPointer;
}

export function setInteractionLocked(shouldLock) {
    shouldLock ? lockUI() : unlockUI();
}